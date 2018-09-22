import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression

object Vasanthi_Mudunuri_Program_1 {
  def main(args:Array[String]) {
  val spark = SparkSession.builder().master("local").appName("Regression").getOrCreate()
  val outputpath="hdfs://hadoop1:9000"+args(0)
  val data= spark.read.format("com.databricks.spark.csv").option("header","true").load("hdfs://hadoop1:9000/vmudunu/WIKI.csv") //reading inputfile
  val requiredData=data.select("AGE","GENDER","PhD","UNIVERSITY") //selecting required attributes
  val  transformData= requiredData.selectExpr("cast(AGE as double) AGE","cast(GENDER as double) GENDER","cast(PhD as double) PhD","cast(UNIVERSITY as double) UNIVERSITY")
  transformData.printSchema()
  val autoFeatures = transformData.columns.slice(0,2)
  val assembledFeatures = new VectorAssembler().setInputCols(autoFeatures).setOutputCol("features")
  val trainingData = assembledFeatures.transform(transformData)
  trainingData.rdd.saveAsTextFile(outputpath+"/TrainingData")
  val regressionObject = new LinearRegression()
      .setMaxIter(1000)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)
      .setFeaturesCol("features")
      .setLabelCol("UNIVERSITY")
      .setPredictionCol("Predicted_University")
  val regressionModel = regressionObject.fit(trainingData) //creating model on training data
  println(s"Coefficients: ${regressionModel.coefficients} Intercept: ${regressionModel.intercept}")
  val trainingSummary = regressionModel.summary
  println(s"numIterations: ${trainingSummary.totalIterations}")
  println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
  val testData = trainingData.limit(1)
    testData.select("UNIVERSITY").rdd.saveAsTextFile(outputpath+"/TestData")
    val PredictedDF = regressionModel.transform(testData)
    PredictedDF.select("Predicted_University").rdd.saveAsTextFile(outputpath+"/PredictedData") //predicted value as output
  }
}
