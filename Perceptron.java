import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;



public class Perceptron{

  public Data data;

//Face
  public double []Face_Weights;//To store weights of faces w = w_1, w_2, w_3, w_4 ...
  public double Face_w_0 = 0;
  public double Face_Result =0;
  public Boolean []Face_Solution;

  //Digits:

  public double[][]Digit_Weights;// To store weight of each digits:w0 = w01, w02...., w1= w11,w12,...,...
  public double []Initial_Weight;// w0_0, w1_0,w2_0,w3_0,w4_0.....w9_0
  public double Digit_Result =0;
  public int []Digit_Solution;
  public Perceptron(Data d)
  {
    this.data = d;
    this.Face_Weights = new double[70*60];
    this.Face_Solution = new Boolean[this.data.LengthofTestLables_Face];
/*    for(int i =0; i<(70*60); i++)
    {
      this.Face_Weights[i] =0;
    }*/
    //System.out.println("Perceptron");
    //System.out.println(this.Calculate_Dot_Product(this.Face_Weights,this.data.TrainingFace[0],70*60));
    //this.Calculate_Face_Weights(10);
    //this.Perceptron_Face_Train(100);
  /*  for(int r=0; r<60*70; r++)
    {
      System.out.print(Face_Weights[r] + "  ");
    }*/
    //this.Face_Testing();
/*
    int counter =0;
    for(int r1 =0; r1<this.data.LengthofTestLables_Face; r1++)
    {
      //System.out.println(this.data.TestLables_Face[r1]+"  "+this.Face_Solution[r1]);
      if(this.data.TestLables_Face[r1] == this.Face_Solution[r1])
      {
        counter = counter +1;
      }
    }
    System.out.println("Counter: "+counter+ "  "+this.data.LengthofTestLables_Face);
*/
    //System.out.println("DIGITS:");
    this.Digit_Weights = new double[10][28*28];
    this.Initial_Weight = new double[10];
    this.Digit_Solution = new int[this.data.LengthofTestLables];
    this.Digit_Constructor();
/*    for(int k =0; k<10;k++)
    {
      this.Initial_Weight[k] = 0;
      for(int k2 =0; k2<28*28; k2++)
      {
        this.Digit_Weights[k][k2] = 0;
      }
    }
    */
    //this.Calculate_Digits_Weights(10);
    //this.Perceptron_Digit_Train(100); // Random
    //this.Digits_Testing();
  /*  int counter2 =0;
    for(int q =0; q<this.data.LengthofTestLables; q++)
    {
      //System.out.println(this.data.TestLables_Face[r1]+"  "+this.Face_Solution[r1]);
      if(this.data.TestLables[q] == this.Digit_Solution[q])
      {
        counter2 = counter2 +1;
      }
    }
    System.out.println("Digit_Counter: "+counter2+ "  "+this.data.LengthofTestLables);
*/
  }
  public void Face_Constructor()
  {
    for(int i =0; i<(70*60); i++)
    {
      this.Face_Weights[i] =0;
    }
  }
  public void Digit_Constructor()
  {
    for(int k =0; k<10;k++)
    {
      this.Initial_Weight[k] = 0;
      for(int k2 =0; k2<28*28; k2++)
      {
        this.Digit_Weights[k][k2] = 0;
      }
    }
  }
  public double get_Digit_Accuracy()
  {
    double check =0;
      for(int o =0; o<this.data.LengthofTestLables; o++)
      {
        if(this.Digit_Solution[o] == this.data.TestLables[o])
        {
          check = check+1;
        }
      }
      check = (check/this.data.LengthofTestLables)*100;
      return check;
  }

  public double get_Face_Accuracy()
  {
    double check =0;

    for(int q =0; q<this.data.LengthofTestLables_Face; q++)
    {
      if(this.Face_Solution[q] == this.data.TestLables_Face[q])
      {
        check = check+1;
      }
    }
    check = (check/this.data.LengthofTestLables_Face)*100;
    return check;
  }

  public void  Calculate_Face_Weights()
  {
    int do_nothing =0;
    //System.out.println("this.data.LengthofTestLables_Face: "+ this.data.LengthofTestLables_Face);
    while(do_nothing != this.data.LengthOfTrainingLables_Face)
    {
    do_nothing =0;
    for(int i =0; i<this.data.LengthOfTrainingLables_Face; i++)
    {
      this.Face_Result =  this.Face_w_0 + this.Calculate_Dot_Product(this.Face_Weights, this.data.TrainingFace[i], 70*60);
      //System.out.println("This.Face_Result: "+this.Face_Result);
      if((this.Face_Result >= 0 && this.data.TrainingFaceLabels[i] == true) ||(this.Face_Result<0 && this.data.TrainingFaceLabels[i] == false))
      {
        //Do Nothing
        do_nothing = do_nothing +1;
      }
      else
      {
        if(this.Face_Result<0 && this.data.TrainingFaceLabels[i] == true)
        {
          for(int k =0; k<70*60;k++)
          {
            if(this.data.TrainingFace[i][k] == '+' || this.data.TrainingFace[i][k] == '#')
            {
                this.Face_Weights[k] = this.Face_Weights[k] +1;
            }
            else{this.Face_Weights[k] = this.Face_Weights[k] -1;}
          }
          this.Face_w_0 = this.Face_w_0 +1;
        }
        if(this.Face_Result >= 0 && this.data.TrainingFaceLabels[i] == false)
        {
          for(int k2 =0; k2<70*60;k2++)
          {
            if(this.data.TrainingFace[i][k2] == '+' || this.data.TrainingFace[i][k2] == '#')
            {
                this.Face_Weights[k2] = this.Face_Weights[k2] - 1;
            }
            else{this.Face_Weights[k2] = this.Face_Weights[k2]+1;}
          }
          this.Face_w_0 = this.Face_w_0 - 1;
        }
      }
    }
  //System.out.println("do_nothing "+do_nothing);
}
  }

  public double Calculate_Dot_Product(double Vector1[], char Vector2[], int size)
  {
    double product =0;
    for(int i =0; i<size; i++)
    {
      if(Vector2[i] == '+' || Vector2[i] == '#')
      { product = product + Vector1[i]*1;}
      else
      {
        product = product + Vector1[i]*-1;
      }
    }
    return product;
  }

  public void Face_Testing()
  {
    for(int i =0; i<this.data.LengthofTestLables_Face; i++)
    {
      this.Face_Result =  this.Face_w_0 + this.Calculate_Dot_Product(this.Face_Weights, this.data.TestData_Face[i], 70*60);

      if(this.Face_Result >= 0)
      {
        this.Face_Solution[i] = true;
      }
      else{ this.Face_Solution[i] = false;}
    }
  }



  public void  Calculate_Digits_Weights()
  {
    for(int i =0; i<10; i++)
    {
    this.Calculate_Digits_Weights_SubFunction(i);
    System.out.println("i = "+ i);
  }
  }



public void Calculate_Digits_Weights_SubFunction(int digit)
{
  int do_nothing =0;
  //for(int ir =0; ir<100; ir++)
  while(do_nothing != this.data.LengthOfTrainingLables)
  {
  do_nothing =0;
  for(int i =0; i<this.data.LengthOfTrainingLables; i++)
  {
    this.Digit_Result =  this.Initial_Weight[digit]+ this.Calculate_Dot_Product(this.Digit_Weights[digit], this.data.TrainingDigits[i], 28*28);
    //System.out.println("This.Face_Result: "+this.Digit_Result);
    if((this.Digit_Result >= 0 && this.data.TrainingDigitLabels[i] == digit) ||(this.Digit_Result<0 && this.data.TrainingDigitLabels[i] != digit))
    {
      //Do Nothing
      do_nothing = do_nothing +1;
    }
    else
    {
      if(this.Digit_Result<0 && this.data.TrainingDigitLabels[i] == digit)
      {
        for(int k =0; k<28*28;k++)
        {
          if(this.data.TrainingDigits[i][k] == '+' || this.data.TrainingDigits[i][k] == '#')
          {
              this.Digit_Weights[digit][k] = this.Digit_Weights[digit][k] +1;
          }
          else{this.Digit_Weights[digit][k] = this.Digit_Weights[digit][k] -1;}
        }
        this.Initial_Weight[digit] = this.Initial_Weight[digit] +1;
      }
      if(this.Digit_Result >= 0 && this.data.TrainingDigitLabels[i] != digit)
      {
        for(int k2 =0; k2<28*28;k2++)
        {
          if(this.data.TrainingDigits[i][k2] == '+' || this.data.TrainingDigits[i][k2] == '#')
          {
              this.Digit_Weights[digit][k2] = this.Digit_Weights[digit][k2] - 1;
          }
          else{this.Digit_Weights[digit][k2] = this.Digit_Weights[digit][k2]+1;}
        }
        this.Initial_Weight[digit] = this.Initial_Weight[digit] - 1;
      }
    }
}
//System.out.println("do_nothing: "+ do_nothing);

}
}

public void Digits_Testing()
{
  int test = 0;
  double []temp = new double[10];
  int max =-1;
  for(int i =0; i<this.data.LengthofTestLables; i++)
  {
    for(int j =0; j<10; j++)
    {
      temp[j] = this.Initial_Weight[j] + this.Calculate_Dot_Product(this.Digit_Weights[j], this.data.TestData[i], 28*28);
    }
      max = 0;
      for(int k =1; k<10; k++)
      {
        if(temp[k]>temp[max])
        {
          max = k;
        }
      }
      this.Digit_Solution[i] = max;

  /*  test =0;
    while(test<10)
    {
    this.Digit_Result =  this.Initial_Weight[test] + this.Calculate_Dot_Product(this.Digit_Weights[test], this.data.TestData[i], 28*28);

    if(this.Face_Result >= 0)
    {
      this.Digit_Solution[i] = test;
      break;
    }
    else{ test = test+1;}
  }*/
}
}

public void  Calculate_Face_Weights(int Percentages)
{
  this.Face_w_0 =0;
  int do_nothing =0;
  //System.out.println("this.data.LengthofTestLables_Face: "+ this.data.LengthofTestLables_Face);
  int limit = this.data.LengthOfTrainingLables_Face*Percentages;
  limit = limit/100;
  while(do_nothing != limit)
  {
  do_nothing =0;
  for(int i =0; i<limit; i++)
  {
    this.Face_Result =  this.Face_w_0 + this.Calculate_Dot_Product(this.Face_Weights, this.data.TrainingFace[i], 70*60);
    //System.out.println("This.Face_Result: "+this.Face_Result);
    if((this.Face_Result >= 0 && this.data.TrainingFaceLabels[i] == true) ||(this.Face_Result<0 && this.data.TrainingFaceLabels[i] == false))
    {
      //Do Nothing
      do_nothing = do_nothing +1;
    }
    else
    {
      if(this.Face_Result<0 && this.data.TrainingFaceLabels[i] == true)
      {
        for(int k =0; k<70*60;k++)
        {
          if(this.data.TrainingFace[i][k] == '+' || this.data.TrainingFace[i][k] == '#')
          {
              this.Face_Weights[k] = this.Face_Weights[k] +1;
          }
          else{this.Face_Weights[k] = this.Face_Weights[k] -1;}
        }
        this.Face_w_0 = this.Face_w_0 +1;
      }
      if(this.Face_Result >= 0 && this.data.TrainingFaceLabels[i] == false)
      {
        for(int k2 =0; k2<70*60;k2++)
        {
          if(this.data.TrainingFace[i][k2] == '+' || this.data.TrainingFace[i][k2] == '#')
          {
              this.Face_Weights[k2] = this.Face_Weights[k2] - 1;
          }
          else{this.Face_Weights[k2] = this.Face_Weights[k2]+1;}
        }
        this.Face_w_0 = this.Face_w_0 - 1;
      }
    }
  }
//System.out.println("do_nothing "+do_nothing);
}

}


  public void  Calculate_Digits_Weights(int Percentages)
  {
    for(int i =0; i<10; i++)
    {
      this.Initial_Weight[i] =0;
    this.Calculate_Digits_Weights_SubFunction(i,Percentages);
    //System.out.println("training for weight "+ i);
  }
  }



public void Calculate_Digits_Weights_SubFunction(int digit, int Percentages)
{
  int limit = this.data.LengthOfTrainingLables * Percentages;
  limit = limit/100;
  int do_nothing =0;
  //for(int ir =0; ir<100; ir++)
  while(do_nothing != limit)
  {
  do_nothing =0;
  for(int i =0; i<limit; i++)
  {
    this.Digit_Result =  this.Initial_Weight[digit]+ this.Calculate_Dot_Product(this.Digit_Weights[digit], this.data.TrainingDigits[i], 28*28);
    //System.out.println("This.Face_Result: "+this.Digit_Result);
    if((this.Digit_Result >= 0 && this.data.TrainingDigitLabels[i] == digit) ||(this.Digit_Result<0 && this.data.TrainingDigitLabels[i] != digit))
    {
      //Do Nothing
      do_nothing = do_nothing +1;
    }
    else
    {
      if(this.Digit_Result<0 && this.data.TrainingDigitLabels[i] == digit)
      {
        for(int k =0; k<28*28;k++)
        {
          if(this.data.TrainingDigits[i][k] == '+' || this.data.TrainingDigits[i][k] == '#')
          {
              this.Digit_Weights[digit][k] = this.Digit_Weights[digit][k] +1;
          }
          else{this.Digit_Weights[digit][k] = this.Digit_Weights[digit][k] -1;}
        }
        this.Initial_Weight[digit] = this.Initial_Weight[digit] +1;
      }
      if(this.Digit_Result >= 0 && this.data.TrainingDigitLabels[i] != digit)
      {
        for(int k2 =0; k2<28*28;k2++)
        {
          if(this.data.TrainingDigits[i][k2] == '+' || this.data.TrainingDigits[i][k2] == '#')
          {
              this.Digit_Weights[digit][k2] = this.Digit_Weights[digit][k2] - 1;
          }
          else{this.Digit_Weights[digit][k2] = this.Digit_Weights[digit][k2]+1;}
        }
        this.Initial_Weight[digit] = this.Initial_Weight[digit] - 1;
      }
    }
}
//System.out.println("do_nothing: "+ do_nothing);

}
}


public void Perceptron_Face_Train(int Percentages)
{
  if(Percentages != 100)
  {
  int []list;
  list = this.data.Calculate_Random_Data_index(Percentages, this.data.LengthOfTrainingLables_Face);
  int m = this.data.LengthOfTrainingLables_Face * Percentages;
  m = m/100;
  this.Calculate_Face_Weights_Prediction(Percentages, list);
}
else{this.Calculate_Face_Weights(100);}
}

public void  Calculate_Face_Weights_Prediction(int Percentages, int list[])
{
  this.Face_w_0 =0;
  int do_nothing =0;
  //System.out.println("this.data.LengthofTestLables_Face: "+ this.data.LengthofTestLables_Face);
  int limit = this.data.LengthOfTrainingLables_Face*Percentages;
  limit = limit/100;
  while(do_nothing != limit)
  {
  do_nothing =0;
  for(int i =0; i<limit; i++)
  {
    this.Face_Result =  this.Face_w_0 + this.Calculate_Dot_Product(this.Face_Weights, this.data.TrainingFace[list[i]], 70*60);
    //System.out.println("This.Face_Result: "+this.Face_Result);
    if((this.Face_Result >= 0 && this.data.TrainingFaceLabels[list[i]] == true) ||(this.Face_Result<0 && this.data.TrainingFaceLabels[list[i]] == false))
    {
      //Do Nothing
      do_nothing = do_nothing +1;
    }
    else
    {
      if(this.Face_Result<0 && this.data.TrainingFaceLabels[list[i]] == true)
      {
        for(int k =0; k<70*60;k++)
        {
          if(this.data.TrainingFace[list[i]][k] == '+' || this.data.TrainingFace[list[i]][k] == '#')
          {
              this.Face_Weights[k] = this.Face_Weights[k] +1;
          }
          else{this.Face_Weights[k] = this.Face_Weights[k] -1;}
        }
        this.Face_w_0 = this.Face_w_0 +1;
      }
      if(this.Face_Result >= 0 && this.data.TrainingFaceLabels[list[i]] == false)
      {
        for(int k2 =0; k2<70*60;k2++)
        {
          if(this.data.TrainingFace[list[i]][k2] == '+' || this.data.TrainingFace[list[i]][k2] == '#')
          {
              this.Face_Weights[k2] = this.Face_Weights[k2] - 1;
          }
          else{this.Face_Weights[k2] = this.Face_Weights[k2]+1;}
        }
        this.Face_w_0 = this.Face_w_0 - 1;
      }
    }
  }
//System.out.println("do_nothing "+do_nothing);
}

}

public void Perceptron_Digit_Train(int Percentages)
{
  if(Percentages != 100)
  {
  int []list;
  list = this.data.Calculate_Random_Data_index(Percentages, this.data.LengthOfTrainingLables);
  int m = this.data.LengthOfTrainingLables * Percentages;
  m = m/100;
  this.Calculate_Digits_Weights_Prediction(Percentages, list);
}
else{this.Calculate_Digits_Weights(100);}
}

public void  Calculate_Digits_Weights_Prediction(int Percentages, int list[])
{
  for(int i =0; i<10; i++)
  {
    this.Initial_Weight[i] =0;
  this.Calculate_Digits_Weights_SubFunction_Prediction(i,Percentages, list);
  //System.out.println("i = "+ i);
}
}


public void Calculate_Digits_Weights_SubFunction_Prediction(int digit, int Percentages, int list[])
{
  int limit = this.data.LengthOfTrainingLables * Percentages;
  limit = limit/100;
  int do_nothing =0;
  //for(int ir =0; ir<100; ir++)
  while(do_nothing != limit)
  {
  do_nothing =0;
  for(int i =0; i<limit; i++)
  {
    this.Digit_Result =  this.Initial_Weight[digit]+ this.Calculate_Dot_Product(this.Digit_Weights[digit], this.data.TrainingDigits[list[i]], 28*28);
    //System.out.println("This.Face_Result: "+this.Digit_Result);
    if((this.Digit_Result >= 0 && this.data.TrainingDigitLabels[list[i]] == digit) ||(this.Digit_Result<0 && this.data.TrainingDigitLabels[list[i]] != digit))
    {
      //Do Nothing
      do_nothing = do_nothing +1;
    }
    else
    {
      if(this.Digit_Result<0 && this.data.TrainingDigitLabels[list[i]] == digit)
      {
        for(int k =0; k<28*28;k++)
        {
          if(this.data.TrainingDigits[list[i]][k] == '+' || this.data.TrainingDigits[list[i]][k] == '#')
          {
              this.Digit_Weights[digit][k] = this.Digit_Weights[digit][k] +1;
          }
          else{this.Digit_Weights[digit][k] = this.Digit_Weights[digit][k] -1;}
        }
        this.Initial_Weight[digit] = this.Initial_Weight[digit] +1;
      }
      if(this.Digit_Result >= 0 && this.data.TrainingDigitLabels[list[i]] != digit)
      {
        for(int k2 =0; k2<28*28;k2++)
        {
          if(this.data.TrainingDigits[list[i]][k2] == '+' || this.data.TrainingDigits[list[i]][k2] == '#')
          {
              this.Digit_Weights[digit][k2] = this.Digit_Weights[digit][k2] - 1;
          }
          else{this.Digit_Weights[digit][k2] = this.Digit_Weights[digit][k2]+1;}
        }
        this.Initial_Weight[digit] = this.Initial_Weight[digit] - 1;
      }
    }
}
//System.out.println("do_nothing: "+ do_nothing);

}
}

//This return array with [digit Accuracy, digit trainig time, face accuracy, face traing time]
public double[] Train_And_Test()
{
  double []Return_Array = new double[40];
  int Percentages =10;
  long startTime;
  long endTime;
  long timeElapsed;
  for(int i =0; i<10;i++)
  {
    this.Digit_Constructor();
    startTime = System.nanoTime();
    this.Calculate_Digits_Weights(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Digits_Testing();
    Return_Array[i] = this.get_Digit_Accuracy();
    Return_Array[i+10] = (double)timeElapsed/1000000;

//Faces:
    this.Face_Constructor();
    startTime = System.nanoTime();
    this.Calculate_Face_Weights(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Face_Testing();
    Return_Array[i+20] = this.get_Face_Accuracy();
    Return_Array[i+30] = (double)timeElapsed/1000000;

    Percentages = Percentages +10;
  }
  return Return_Array;
}

public double[] Train_And_Test_Random()
{
  double []Return_Array = new double[40];
  int Percentages =10;
  long startTime;
  long endTime;
  long timeElapsed;
  for(int i =0; i<10;i++)
  {
    this.Digit_Constructor();
    startTime = System.nanoTime();
    this.Perceptron_Digit_Train(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Digits_Testing();
    Return_Array[i] = this.get_Digit_Accuracy();
    Return_Array[i+10] = (double)timeElapsed/1000000;

//Faces:
    this.Face_Constructor();
    startTime = System.nanoTime();
    this.Perceptron_Face_Train(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Face_Testing();
    Return_Array[i+20] = this.get_Face_Accuracy();
    Return_Array[i+30] = (double)timeElapsed/1000000;

    Percentages = Percentages +10;
  }
  return Return_Array;
}

//Return_Array[] = [ digit mean, digit Standard_Deviation, face mean, face std]
public double[] Standard_Deviation()
{
  double []Return_Array = new double[40];
  for(int y =0; y<40;y++)
  {Return_Array[y] =0;}

  double []acc = new double[5];
  int Percentages =10;
  long startTime;
  long endTime;
  long timeElapsed;
  for(int i =0; i<10;i++)
  {
    for(int j =0; j<5; j++)
    {
      this.Digit_Constructor();
      this.Perceptron_Digit_Train(Percentages);
      this.Digits_Testing();
      acc[j] = this.get_Digit_Accuracy();
    }
    //Calculate mean and Standard_Deviation:

    for(int k =0; k<5; k++)
    {
      Return_Array[i] = Return_Array[i]+acc[k];
    }

    Return_Array[i] = Return_Array[i]/5;//mean

    for(int l =0; l<5;l++)
    {
      //std = sqrt(((acc[l] - Return_Array[i])^2)/4)
      Return_Array[i+10] = Return_Array[i+10] + Math.pow((acc[l] - Return_Array[i]),2);
    }
    Return_Array[i+10] = Return_Array[i+10]/4;
    Return_Array[i+10] = Math.sqrt(Return_Array[i+10]);

//Faces:
    for(int m =0; m<5;m++)
    {
      this.Face_Constructor();
      this.Perceptron_Face_Train(Percentages);
      this.Face_Testing();
      acc[m] = this.get_Face_Accuracy();
    }
    for(int n =0; n<5; n++)
    {
      Return_Array[i+20] = Return_Array[i+20]+acc[n];
    }
    Return_Array[i+20] = Return_Array[i+20]/5;//mean

    for(int o =0; o<5;o++)
    {
      //std = sqrt(((acc[l] - Return_Array[i])^2)/4)
      Return_Array[i+30] = Return_Array[i+30] + Math.pow((acc[o] - Return_Array[i+20]),2);
    }
    Return_Array[i+30] = Return_Array[i+30]/4;
    Return_Array[i+30] = Math.sqrt(Return_Array[i+30]);
    Percentages = Percentages +10;
  }
  return Return_Array;
}

}
