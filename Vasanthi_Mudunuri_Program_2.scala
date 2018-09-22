import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.clustering.KMeans

object Vasanthi_Mudunuri_Program_2 {
  def main(args:Array[String]){
  val spark = SparkSession.builder().master("local").appName("Clustering").getOrCreate()
  val data= spark.read.format("com.databricks.spark.csv").option("header","true").load("hdfs://hadoop1:9000/vmudunu/WIKI.csv") //loading input file
  val outputpath="hdfs://hadoop1:9000"+args(0)
  val requiredData=data.select("UOC_POSITION","PU1","PU2","PU3") //selecting required attributes
  val  transformData= requiredData.selectExpr("cast(UOC_POSITION as double) UOC_POSITION","cast(PU1 as double) PU1","cast(PU2 as double) PU2","cast(PU3 as double) PU3")
  transformData.printSchema()
  transformData.show()
  val autoFeatures = transformData.columns
  val assembledFeatures = new VectorAssembler().setInputCols(autoFeatures).setOutputCol("features")
  val trainingData = assembledFeatures.transform(transformData.na.drop)
  trainingData.rdd.saveAsTextFile(outputpath+"/TrainingData")
  val kmeans = new KMeans().setK(4).setSeed(1L) 
  val model = kmeans.fit(trainingData)
  val WSSSE = model.computeCost(trainingData)
  model.clusterCenters.foreach(println) //prints cluster centers on console
  model.summary.predictions.rdd.saveAsTextFile(outputpath+"/Prediction") //saving predictions to output file
  }
}
