import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.lang.Math;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main{

public static void main(String[] args) throws IOException {

Data n = new Data(".\\data\\digitdata\\trainingimages", ".\\data\\digitdata\\traininglabels", ".\\data\\digitdata\\testimages", ".\\data\\digitdata\\testlabels",
                  ".\\data\\digitdata\\validationimages", ".\\data\\digitdata\\validationlabels",
                  ".\\data\\facedata\\facedatatrain", ".\\data\\facedata\\facedatatrainlabels",".\\data\\facedata\\facedatatest", ".\\data\\facedata\\facedatatestlabels",
                  ".\\data\\facedata\\facedatavalidation", ".\\data\\facedata\\facedatavalidationlabels");


NaiveBayes naivebayes = new NaiveBayes(n);
Perceptron perceptron = new Perceptron(n);
System.out.print("Menu: \n1. Train specific % of DIGIT traing data using NaiveBayes classifier and Test.\n");
System.out.print("2. Train specific % of FACE traing data using NaiveBayes classifier and Test.\n");
System.out.print("3. Train specific % of DIGIT traing data using Perceptron classifier and Test.\n");
System.out.print("4. Train specific % of FACE traing data using Perceptron classifier and Test.\n");
System.out.print("5. Train specific % of DIGIT (Random) traing data using NaiveBayes classifier and Test.\n");
System.out.print("6. Train specific % of FACE (Random) traing data using NaiveBayes classifier and Test.\n");
System.out.print("7. Train specific % of DIGIT (Random) traing data using Perceptron classifier and Test.\n");
System.out.print("8. Train specific % of FACE (Random)traing data using Perceptron classifier and Test.\n");
System.out.print("9. Train and test using 10%, 20%, ...., 100% of trainig data.\n");
System.out.print("10. Train and test using Random 10%, 20%,...,100% of training data.\n");
System.out.print("11. Standard Deviation for NaiveBayes and Perceptron classifier.\n");
System.out.print("Press any other key to Quit. \n ");
Scanner myObj = new Scanner(System.in);
System.out.print("Pick one of the option from menu: ");
String menu = myObj.nextLine();  // Read user input
//System.out.println("\n");
//System.out.println("Username is: " + menu);  // Output user input

long startTime;
long endTime;
long timeElapsed;
String opt;
int option;
System.out.print("\n");
switch(menu)
{
  case "1":
      System.out.print("Enter % to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      naivebayes.Digit_constructor();
      startTime = System.nanoTime();
      naivebayes.NaiveBayes_Train_Digits(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      naivebayes.Testing();
      System.out.println("NaiveBayes: DIGIT CLASSIFIER ");
      System.out.println("Percentage of Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      //naivebayes.print_Prior_Digit_Distribution();
      System.out.println("Accuracy of Testing: "+String.format("%.2f",naivebayes.get_Digit_Accuracy())+" %");
      break;
  case "2":
      System.out.print("Enter % to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      naivebayes.Face_constructor();
      startTime = System.nanoTime();
      naivebayes.NaiveBayes_Train_Faces(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      naivebayes.Testing_Face();
      System.out.println("NaiveBayes: FACE CLASSIFIER ");
      System.out.println("Percentage of Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      //naivebayes.print_Prior_Face_Distribution();
      System.out.println("Accuracy of Testing: "+String.format("%.2f",naivebayes.get_Face_Accuracy())+" %");
      break;
  case "3":
      System.out.print("Enter % to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      perceptron.Digit_Constructor();
      startTime = System.nanoTime();
      perceptron.Calculate_Digits_Weights(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      perceptron.Digits_Testing();
      System.out.println("Perceptron: DIGIT CLASSIFIER ");
      System.out.println("Percentage of Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      System.out.println("Accuracy of Testing: "+String.format("%.2f",perceptron.get_Digit_Accuracy())+" %");
      break;
  case "4":
      System.out.print("Enter % to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      perceptron.Face_Constructor();
      startTime = System.nanoTime();
      perceptron.Calculate_Face_Weights(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      perceptron.Face_Testing();
      System.out.println("Perceptron: Face CLASSIFIER ");
      System.out.println("Percentage of Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      System.out.println("Accuracy of Testing: "+String.format("%.2f",perceptron.get_Face_Accuracy())+" %");
      break;
  case "5":
      System.out.print("Enter % Random to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      naivebayes.Digit_constructor();
      startTime = System.nanoTime();
      naivebayes.Prediction_Train_Digit(option);
      //naivebayes.NaiveBayes_Train_Digits(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      naivebayes.Testing();
      System.out.println("NaiveBayes: DIGIT CLASSIFIER ");
      System.out.println("Percentage of Random Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
          //naivebayes.print_Prior_Digit_Distribution();
      System.out.println("Accuracy of Testing: "+String.format("%.2f",naivebayes.get_Digit_Accuracy())+" %");
      break;
  case "6":
      System.out.print("Enter % Random to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      naivebayes.Face_constructor();
      startTime = System.nanoTime();
      naivebayes.Prediction_Train_Face(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      naivebayes.Testing_Face();
      System.out.println("NaiveBayes: FACE CLASSIFIER ");
      System.out.println("Percentage of Random Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      //naivebayes.print_Prior_Face_Distribution();
      System.out.println("Accuracy of Testing: "+String.format("%.2f",naivebayes.get_Face_Accuracy())+" %");
      break;
  case "7":
      System.out.print("Enter % to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      perceptron.Digit_Constructor();
      startTime = System.nanoTime();
      perceptron.Perceptron_Digit_Train(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      perceptron.Digits_Testing();
      System.out.println("Perceptron: DIGIT CLASSIFIER ");
      System.out.println("Percentage of Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      System.out.println("Accuracy of Testing: "+String.format("%.2f",perceptron.get_Digit_Accuracy())+" %");
      break;
  case "8":
      System.out.print("Enter % to train data: ");
      opt = myObj.nextLine();
      option = Integer.parseInt(opt);
      perceptron.Face_Constructor();
      startTime = System.nanoTime();
      perceptron.Perceptron_Face_Train(option);
      endTime = System.nanoTime();
      timeElapsed = endTime - startTime;

      perceptron.Face_Testing();
      System.out.println("Perceptron: Face CLASSIFIER ");
      System.out.println("Percentage of Data points used: "+option+ " %");
      System.out.println("Time to Train: "+timeElapsed/1000000 +" miliseconds");
      System.out.println("Accuracy of Testing: "+String.format("%.2f",perceptron.get_Face_Accuracy())+" %");
      break;
  case "9":
      double []array;
      array = naivebayes.Train_And_Test();
      System.out.println("NAIVEBAYES CLASSIFIER: ");
      System.out.println("% of data\tDigit Accuracy\tDigit time\tFace Accuracy\tFace time");
      int per =10;
      for(int j =0; j<10; j++)
      {
        System.out.println(per+"\t\t"+String.format("%.2f",array[j])+"\t\t"+String.format("%6.2f",array[j+10])+"\t\t"+String.format("%.2f",array[j+20])+"\t\t"+String.format("%.2f",array[j+30]));
        per = per+10;
      }

      double []Array;
      Array = perceptron.Train_And_Test();
      System.out.println("Training In Process....");
      System.out.println("PERCPTRON CLASSIFIER: ");
      System.out.println("% of data\tDigit Accuracy\tDigit time\tFace Accuracy\tFace time");
      per =10;
      for(int i =0; i<10; i++)
      {
        System.out.println(per+"\t\t"+String.format("%.2f",Array[i])+"\t\t"+String.format("%6.2f",Array[i+10])+"\t\t"+String.format("%.2f",Array[i+20])+"\t\t"+String.format("%.2f",Array[i+30]));
        per = per+10;
      }
      break;
  case "10":
      int percent;
      double []array1;
      array1 = naivebayes.Train_And_Test_Random();
      System.out.println("NAIVEBAYES CLASSIFIER: ");
      System.out.println("Random % of data\tDigit Accuracy\tDigit time\tFace Accuracy\tFace time");
      percent =10;
      for(int j =0; j<10; j++)
      {
        System.out.println(percent+"\t\t"+String.format("%.2f",array1[j])+"\t\t"+String.format("%6.2f",array1[j+10])+"\t\t"+String.format("%.2f",array1[j+20])+"\t\t"+String.format("%.2f",array1[j+30]));
        percent = percent+10;
      }
      System.out.println("Training in Process...");
      double []Array1;
      Array1 = perceptron.Train_And_Test_Random();
      System.out.println("PERCPTRON CLASSIFIER: ");
      System.out.println("% of data\tDigit Accuracy\tDigit time\tFace Accuracy\tFace time");
      percent =10;
      for(int i =0; i<10; i++)
      {
        System.out.println(percent+"\t\t"+String.format("%.2f",Array1[i])+"\t\t"+String.format("%6.2f",Array1[i+10])+"\t\t"+String.format("%.2f",Array1[i+20])+"\t\t"+String.format("%.2f",Array1[i+30]));
        percent = percent+10;
      }
      break;
  case "11":
      int p;
      double []array2;
      System.out.println("Processing.....");
      array2 = naivebayes.Standard_Deviation();
      System.out.println("NAIVEBAYES: ");
      System.out.println("% of data\tDigit mean\tDigit std\tFace mean\tFace std");
      p =10;
      for(int l =0; l<10; l++)
      {
        System.out.println(p+"\t\t"+String.format("%.2f",array2[l])+"\t\t"+String.format("%6.2f",array2[l+10])+"\t\t"+String.format("%.2f",array2[l+20])+"\t\t"+String.format("%.2f",array2[l+30]));
        p = p+10;
      }

      p = 10;
      double []Array2;
      System.out.println("Processing........");
      Array2 = perceptron.Standard_Deviation();
      System.out.println("PERCPTRON: ");
      System.out.println("% of data\tDigit mean\tDigit std\tFace mean\tFace std");
      for(int m = 0; m<10;m++)
      {
        System.out.println(p+"\t\t"+String.format("%.2f",Array2[m])+"\t\t"+String.format("%6.2f",Array2[m+10])+"\t\t"+String.format("%.2f",Array2[m+20])+"\t\t"+String.format("%.2f",Array2[m+30]));
        p = p+10;
      }
      break;
  default:
      //System.out.println("Default argument");
      break;
}
/*
for(int i =0; i<8;i++)
{
  System.out.println("Digit: "+ n.ValidationLables[i]);
  for(int j =0; j<28*28;j++)
  {
    System.out.print(n.ValidationData[i][j]);
    if(j%28 == 0)
    {
      System.out.print("\n");
    }
  }
  System.out.print("\n");
}
*/
/*
int f=0;
NaiveBayes m = new NaiveBayes(n);
for(int i =0; i<m.data.LengthofTestLables;i++)
{
  if(m.data.TestLables[i] == m.TestSolution[i])
  {
    f =f+1;
  }
}
System.out.println("f = " + f +" f/n = "+m.data.LengthofTestLables);
*/

//NaiveBayes j = new NaiveBayes(n);
//Perceptron p = new Perceptron(n);
/*
Perceptron p = new Perceptron(n);
int f=0;
for(int i =0; i<p.data.LengthofTestLables;i++)
{
  if(p.data.TestLables[i] == p.Digit_Solution[i])
  {
    f =f+1;
  }
}
System.out.println("f = " + f +" f/n = "+p.data.LengthofTestLables);
}*/
}
}
