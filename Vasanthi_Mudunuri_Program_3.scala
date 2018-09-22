import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer,
VectorAssembler}

object Vasanthi_Mudunuri_Program_3 {
  def main(args:Array[String]){
  val spark = SparkSession.builder().master("local").appName("Regression").getOrCreate()
  val outputpath="hdfs://hadoop1:9000"+args(0)
  val data= spark.read.format("com.databricks.spark.csv").option("header","true").load("hdfs://hadoop1:9000/vmudunu/WIKI.csv") //loading inputfile
  val requiredData=data.select("AGE","GENDER","DOMAIN","PhD","UOC_POSITION","Use1") //selecting required attributes
  val  transformData= requiredData.selectExpr("cast(AGE as double) AGE","cast(GENDER as double) GENDER",
      "cast(DOMAIN as double) DOMAIN","cast(PhD as double) PhD","cast(UOC_POSITION as double) UOC_POSITION","cast(Use1 as double) Use1")
  transformData.printSchema()
  val autoFeatures = transformData.columns.slice(0,4) //dropping output column
  val assembledFeatures = new VectorAssembler().setInputCols(autoFeatures).setOutputCol("features")
  val Array(trainingData, testData) = transformData.na.drop.randomSplit(Array(0.7, 0.3))
  val decissionTreeClassifier = new DecisionTreeClassifier().setLabelCol("Use1").setFeaturesCol("features")
  val pipeline = new Pipeline().setStages(Array(assembledFeatures, decissionTreeClassifier))
  val model = pipeline.fit(trainingData.na.drop)
  val predictions = model.transform(testData)
  predictions.rdd.saveAsTextFile(outputpath+"/Predictions") //saving predictions to outputfile
  val evaluator = new MulticlassClassificationEvaluator().setLabelCol("Use1").setPredictionCol("prediction").setMetricName("accuracy")
  val accuracy = evaluator.evaluate(predictions)
  println("Test Error = " + (1.0 - accuracy)) //error in predicted values
  val treeModel =model.stages(1).asInstanceOf[DecisionTreeClassificationModel]
  println("Learned classification tree model:\n" + treeModel.toDebugString) //displays tree model on console
  }
}
