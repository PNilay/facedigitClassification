// naiveBayes.java
import java.lang.Math;
import java.util.Random;

public class NaiveBayes{

//Digit Data:
public Data data;
public double []Prior_Digit_Distribution; //Prior distribution over label P(Y).
public int []Y_true_in_Xtrain;//This array stores how many times each digits appear  in training data.
public double [][]ProbabilityDistribution;// This stores probability of "+" or '#' given y

public double []LogProbability;
public int []TestSolution;

//Face Data:
public double []Prior_Face_Distribution;
public int Number_of_faces;//Stores total number of faces in training Data
public double [][]ProbabilityDistribution_Face;//This stores probability of "+" or '#' given y = (face or not face)

public double []LogProbability_Face;
public Boolean []TestSolution_Face;

public NaiveBayes(Data d)
{

  //Digit
  this.data = d;
  this.Prior_Digit_Distribution = new double[10];
  this.Y_true_in_Xtrain = new int[10];
  this.ProbabilityDistribution = new double[10][28*28];

  this.LogProbability = new double[10];
  this.TestSolution = new int[this.data.LengthofTestLables];

  this.Digit_constructor();

  //this.Calculate_Prior_Digit_Distribution(100);
  //this.Calculate_Probability_Distribution(100);
  //this.NaiveBayes_Train_Digits(100);
  //this.Prediction_Train_Digit(100);
  //this.Testing();
/*
  int check =0;
    for(int o =0; o<this.data.LengthofTestLables; o++)
    {
      if(this.TestSolution[o] == this.data.TestLables[o])
      {
        check = check+1;
      }
    }
    System.out.println("This.get_Digit_Accuracy "+ this.get_Digit_Accuracy());

    System.out.println("Check: "+check+"  "+ this.data.LengthofTestLables);
*/
//Face:
this.Prior_Face_Distribution = new double[2];
this.LogProbability_Face = new double[2];
this.TestSolution_Face = new Boolean[this.data.LengthofTestLables_Face];
this.ProbabilityDistribution_Face = new double[2][70*60];
this.Face_constructor();

//this.Calculate_Prior_Face_Distribution(50);
//this.Calculate_Probability_Distribution_Face(50);
//this.NaiveBayes_Train_Faces(100);
//this.Prediction_Train_Face(30);
//this.Testing_Face();
//System.out.println("Accuracy: "+this.get_Face_Accuracy());

}

public void Digit_constructor()
{
  for(int r=0; r<this.data.LengthofTestLables; r++)
  {
    this.TestSolution[r] = -1;
  }
    for(int i =0; i<10; i++)
    {
      this.Prior_Digit_Distribution[i] = 0;
      this.Y_true_in_Xtrain[i] =0;

      this.LogProbability[i] =0;

    for(int j =0;j<28*28;j++)
    {
      this.ProbabilityDistribution[i][j] =0;
    }
  }
}

public void Face_constructor()
{
  for(int r1 =0; r1<2;r1++)
  {
    this.Prior_Face_Distribution[r1] = 0;
    this.LogProbability_Face[r1] =0;
    for(int j =0;j<70*60;j++)
    {
      this.ProbabilityDistribution_Face[r1][j] =0;
    }
  }
}
public double get_Digit_Accuracy()
{
  double check =0;
    for(int o =0; o<this.data.LengthofTestLables; o++)
    {
      if(this.TestSolution[o] == this.data.TestLables[o])
      {
        check = check+1;
      }
    }
    //System.out.println("CHECK: "+check);
    check = (check/this.data.LengthofTestLables)*100;
    return check;
}

public double get_Face_Accuracy()
{
  double check =0;

  for(int q =0; q<this.data.LengthofTestLables_Face; q++)
  {
    if(this.TestSolution_Face[q] == this.data.TestLables_Face[q])
    {
      check = check+1;
    }
  }
  check = (check/this.data.LengthofTestLables_Face)*100;
  return check;
}

//Parameter Estimation:
/*
This function calculate Prior Digit Distribution using formula:
  P(y = true) = (Number of times yi = true in X_train)/n
*/
public void Calculate_Prior_Digit_Distribution()
{
  double km =0;

  for(int j =0; j<this.data.LengthOfTrainingLables; j++)
  {
    this.Y_true_in_Xtrain[this.data.TrainingDigitLabels[j]] = this.Y_true_in_Xtrain[this.data.TrainingDigitLabels[j]] + 1;
  }

  for(int k =0; k<10;k++)
  {
    this.Prior_Digit_Distribution[k] = (double)this.Y_true_in_Xtrain[k]/this.data.LengthOfTrainingLables;
    //System.out.println(this.Y_true_in_Xtrain[k] +" , "+ String.format("%.5f",this.Prior_Digit_Distribution[k]));
    //km = this.Prior_Digit_Distribution[k] + km;
  }
  //System.out.println("km = "+ String.format("%.4f", km));
}

public void Calculate_Prior_Face_Distribution()
{
  double km =0;
  this.Number_of_faces =0;

  for(int j =0; j<this.data.LengthOfTrainingLables_Face; j++)
  {
    if(this.data.TrainingFaceLabels[j] == true)
    {
      this.Number_of_faces = this.Number_of_faces +1;
    }
  }

    this.Prior_Face_Distribution[0] = (double)this.Number_of_faces/this.data.LengthOfTrainingLables_Face;
    this.Prior_Face_Distribution[1] = 1-this.Prior_Face_Distribution[0];

    //System.out.println(this.Number_of_faces +" , "+ String.format("%.5f",this.Prior_Face_Distribution[0]));
    //System.out.println(this.data.LengthOfTrainingLables_Face - this.Number_of_faces +" , "+ String.format("%.5f",this.Prior_Face_Distribution[1]));
    //km = this.Prior_Face_Distribution[0]+this.Prior_Face_Distribution[1];
    //System.out.println("km = "+ km);
}


/*
  Calculate_Probability_Distribution funvtion calculates the conditional probability of our featchers
  giveen each lable y: P(F_i = f_i | Y = y)
  f_i = 0 or 1
    0: if array containg " "
    1: if array contains "+" or "#"

    P(F_i = f_i |Y =y) = (Number of times F_i = f_i and Y = y in Xtrain)/number of times Y =y in Xtrain
*/
public void Calculate_Probability_Distribution()
{
  for(int i=0; i<this.data.LengthOfTrainingLables; i++)
  {
    for(int j=0; j<28*28; j++)
    {
      if(this.data.TrainingDigits[i][j] == '+' ||this.data.TrainingDigits[i][j] == '#')
      {
        this.ProbabilityDistribution[this.data.TrainingDigitLabels[i]][j] = this.ProbabilityDistribution[this.data.TrainingDigitLabels[i]][j] +1;}
    }
  }
  double temp = 0;
  for(int m =0; m<10; m++)
  {
    for(int k =0; k<28*28; k++)
    {
      temp = this.ProbabilityDistribution[m][k];
      this.ProbabilityDistribution[m][k] = temp/this.Y_true_in_Xtrain[m];
    }
  }
}

public void Calculate_Probability_Distribution_Face()
{
  for(int i=0; i<this.data.LengthOfTrainingLables_Face; i++)
  {
    for(int j=0; j<70*60; j++)
    {
      if(this.data.TrainingFace[i][j] == '+' ||this.data.TrainingFace[i][j] == '#')
      {
        if(this.data.TrainingFaceLabels[i] == true)
        {
          this.ProbabilityDistribution_Face[0][j] = this.ProbabilityDistribution_Face[0][j] +1;
        }
        else
        {
          this.ProbabilityDistribution_Face[1][j] = this.ProbabilityDistribution_Face[1][j]+1;
        }
      }
    }
  }

  double temp = 0;
  for(int m =0; m<2; m++)
  {
    for(int k =0; k<70*60; k++)
    {
      temp = this.ProbabilityDistribution_Face[m][k];
      this.ProbabilityDistribution_Face[m][k] = temp/this.Number_of_faces;
      //this.Y_true_in_Xtrain[m];
    }

  }
}

public void Testing()
{
  int solution;
  for(int i =0; i<this.data.LengthofTestLables; i++)
  {
    for(int j=0; j<10; j++)
    {
    this.LogProbability[j] = Math.log(this.Prior_Digit_Distribution[j]);
      //this.LogProbability[j] = this.Prior_Digit_Distribution[j];
      for(int k =0;k<28*28; k++)
      {
        if(this.data.TestData[i][k] == '+' || this.data.TestData[i][k] == '#' )
        {
          this.LogProbability[j] = this.LogProbability[j] + Math.log(this.ProbabilityDistribution[j][k]);
          //this.LogProbability[j] = this.LogProbability[j]*this.ProbabilityDistribution[j][k];

        }
        else
        {
          this.LogProbability[j] = this.LogProbability[j] + Math.log(1-this.ProbabilityDistribution[j][k]);
          //this.LogProbability[j] = this.LogProbability[j]*(1-this.ProbabilityDistribution[j][k]);
        }
      }
      //System.out.println(this.LogProbability[j]);

    }
    solution = FindMax();
    this.TestSolution[i] = solution;
  }
}

public int FindMax()
{
  int temp_max =0;
  for(int i =1; i<10; i++)
  {
    if(this.LogProbability[i]>this.LogProbability[temp_max])
    {
      temp_max = i;
    }
  }
  return temp_max;
}

public void Testing_Face()
{
  for(int i =0; i<this.data.LengthofTestLables_Face; i++)
  {
    for(int j=0; j<2; j++)
    {
    this.LogProbability_Face[j] = Math.log(this.Prior_Face_Distribution[j]);

      for(int k =0;k<70*60; k++)
      {
        if(this.data.TestData_Face[i][k] == '+' || this.data.TestData_Face[i][k] == '#' )
        {
          this.LogProbability_Face[j] = this.LogProbability_Face[j] + Math.log(this.ProbabilityDistribution_Face[j][k]);
        }
        else
        {
          this.LogProbability_Face[j] = this.LogProbability_Face[j] + Math.log(1-this.ProbabilityDistribution_Face[j][k]);
        }
      }
    }

    if(this.LogProbability_Face[0]>this.LogProbability_Face[1])
    {
      this.TestSolution_Face[i] = true;
    }
    else
    {
      this.TestSolution_Face[i] = false;
    }
  }
}


//*****************************************************************************//
public void Calculate_Prior_Digit_Distribution(int Percentages)
{
  double km =0;
  int m = this.data.LengthOfTrainingLables * Percentages;
  m = m/100;
  for(int j =0; j<m; j++)
  {
    this.Y_true_in_Xtrain[this.data.TrainingDigitLabels[j]] = this.Y_true_in_Xtrain[this.data.TrainingDigitLabels[j]] + 1;
  }

  for(int k =0; k<10;k++)
  {
    this.Prior_Digit_Distribution[k] = (double)this.Y_true_in_Xtrain[k]/m;
  }
}

public void print_Prior_Digit_Distribution()
{
  for(int k =0; k<10; k++)
  {
    //System.out.println("P("+k+") :\t"+this.Y_true_in_Xtrain[k] +" , "+ String.format("%.5f",this.Prior_Digit_Distribution[k]));
    System.out.println("P("+k+") :\t"+ String.format("%.5f",this.Prior_Digit_Distribution[k]));

  }
}
public void Calculate_Probability_Distribution(int Percentages)
{
  int mk = this.data.LengthOfTrainingLables * Percentages;
  mk = mk/100;
  for(int i=0; i<mk; i++)
  {
    for(int j=0; j<28*28; j++)
    {
      if(this.data.TrainingDigits[i][j] == '+' ||this.data.TrainingDigits[i][j] == '#')
      {
        this.ProbabilityDistribution[this.data.TrainingDigitLabels[i]][j] = this.ProbabilityDistribution[this.data.TrainingDigitLabels[i]][j] +1;}
    }
  }

  double temp = 0;
  for(int m =0; m<10; m++)
  {
    for(int k =0; k<28*28; k++)
    {
      temp = this.ProbabilityDistribution[m][k];
      this.ProbabilityDistribution[m][k] = temp/this.Y_true_in_Xtrain[m];
    }
  }
}

public void NaiveBayes_Train_Digits(int Percentages)
{
  this.Calculate_Prior_Digit_Distribution(Percentages);
  this.Calculate_Probability_Distribution(Percentages);
}


public void Calculate_Prior_Face_Distribution(int Percentages)
{
  double km =0;
  this.Number_of_faces =0;
  int per = this.data.LengthOfTrainingLables_Face * Percentages;
  per = per/100;

  for(int j =0; j<per; j++)
  {
    if(this.data.TrainingFaceLabels[j] == true)
    {
      this.Number_of_faces = this.Number_of_faces +1;
    }
  }

    this.Prior_Face_Distribution[0] = (double)this.Number_of_faces/per;
    this.Prior_Face_Distribution[1] = 1-this.Prior_Face_Distribution[0];

    //System.out.println(this.Number_of_faces +" , "+ String.format("%.5f",this.Prior_Face_Distribution[0]));
    //System.out.println(per - this.Number_of_faces +" , "+ String.format("%.5f",this.Prior_Face_Distribution[1]));
    //km = this.Prior_Face_Distribution[0]+this.Prior_Face_Distribution[1];
    //System.out.println("km = "+ km);
}

public void print_Prior_Face_Distribution()
{
  System.out.println("P(Face) :\t"+ String.format("%.5f",this.Prior_Face_Distribution[0]));
  System.out.println("P(Not Face) :\t"+ String.format("%.5f",this.Prior_Face_Distribution[1]));
}

public void Calculate_Probability_Distribution_Face(int Percentages)
{
  int per = this.data.LengthOfTrainingLables_Face * Percentages;
  per = per/100;
  for(int i=0; i<per; i++)
  {
    for(int j=0; j<70*60; j++)
    {
      if(this.data.TrainingFace[i][j] == '+' ||this.data.TrainingFace[i][j] == '#')
      {
        if(this.data.TrainingFaceLabels[i] == true)
        {
          this.ProbabilityDistribution_Face[0][j] = this.ProbabilityDistribution_Face[0][j] +1;
        }
        else
        {
          this.ProbabilityDistribution_Face[1][j] = this.ProbabilityDistribution_Face[1][j]+1;
        }
      }
    }
  }

  double temp = 0;
  for(int m =0; m<2; m++)
  {
    for(int k =0; k<70*60; k++)
    {
      temp = this.ProbabilityDistribution_Face[m][k];
      this.ProbabilityDistribution_Face[m][k] = temp/this.Number_of_faces;
    }
  }
}

public void NaiveBayes_Train_Faces(int Percentages)
{
  this.Calculate_Prior_Face_Distribution(Percentages);
  this.Calculate_Probability_Distribution_Face(Percentages);
}

//Prediction Error:

public void Prediction_Train_Digit(int Percentages)
{
  if(Percentages != 100)
  {
  int []list;
  list = this.data.Calculate_Random_Data_index(Percentages, this.data.LengthOfTrainingLables);
  int m = this.data.LengthOfTrainingLables * Percentages;
  m = m/100;
  this.Calculate_Prior_Digit_Distribution_Random_Data(Percentages, list);
  this.Calculate_Probability_Distribution_Random_Data(Percentages, list);
}
else
{
  this.NaiveBayes_Train_Digits(100);
}

}
public void Calculate_Prior_Digit_Distribution_Random_Data(int Percentages, int list[])
{
  double km =0;
  int m = this.data.LengthOfTrainingLables * Percentages;
  m = m/100;
  for(int j =0; j<m; j++)
  {
    this.Y_true_in_Xtrain[this.data.TrainingDigitLabels[list[j]]] = this.Y_true_in_Xtrain[this.data.TrainingDigitLabels[list[j]]] + 1;
  }

  for(int k =0; k<10;k++)
  {
    this.Prior_Digit_Distribution[k] = (double)this.Y_true_in_Xtrain[k]/m;
    //System.out.println(this.Y_true_in_Xtrain[k] +" , "+ String.format("%.5f",this.Prior_Digit_Distribution[k]));
    km = this.Prior_Digit_Distribution[k] + km;
  }
  //System.out.println("km = "+ String.format("%.4f", km));
}

public void Calculate_Probability_Distribution_Random_Data(int Percentages, int list[])
{
  int mk = this.data.LengthOfTrainingLables * Percentages;
  mk = mk/100;
  for(int i=0; i<mk; i++)
  {
    for(int j=0; j<28*28; j++)
    {
      if(this.data.TrainingDigits[list[i]][j] == '+' ||this.data.TrainingDigits[list[i]][j] == '#')
      {
        this.ProbabilityDistribution[this.data.TrainingDigitLabels[list[i]]][j] = this.ProbabilityDistribution[this.data.TrainingDigitLabels[list[i]]][j] +1;
  }
    }
  }


  double temp = 0;
  for(int m =0; m<10; m++)
  {
    for(int k =0; k<28*28; k++)
    {
      temp = this.ProbabilityDistribution[m][k];
      this.ProbabilityDistribution[m][k] = temp/this.Y_true_in_Xtrain[m];
    }
  }


}

public void Prediction_Train_Face(int Percentages)
{
  if(Percentages != 100)
  {
  int []list;
  list = this.data.Calculate_Random_Data_index(Percentages, this.data.LengthOfTrainingLables_Face);
  int m = this.data.LengthOfTrainingLables_Face * Percentages;
  m = m/100;
  this.Calculate_Prior_Face_Distribution_Random_Data(Percentages, list);
  this.Calculate_Probability_Distribution_Face_Random_Data(Percentages, list);
}
else
{
  this.NaiveBayes_Train_Faces(100);
}
}

public void Calculate_Prior_Face_Distribution_Random_Data(int Percentages, int list[])
{
  double km =0;
  this.Number_of_faces =0;
  int per = this.data.LengthOfTrainingLables_Face * Percentages;
  per = per/100;

  for(int j =0; j<per; j++)
  {
    if(this.data.TrainingFaceLabels[list[j]] == true)
    {
      this.Number_of_faces = this.Number_of_faces +1;
    }
  }

    this.Prior_Face_Distribution[0] = (double)this.Number_of_faces/per;
    this.Prior_Face_Distribution[1] = 1-this.Prior_Face_Distribution[0];

    //System.out.println(this.Number_of_faces +" , "+ String.format("%.5f",this.Prior_Face_Distribution[0]));
    //System.out.println(per - this.Number_of_faces +" , "+ String.format("%.5f",this.Prior_Face_Distribution[1]));
    //km = this.Prior_Face_Distribution[0]+this.Prior_Face_Distribution[1];
    //System.out.println("km = "+ km);
}

public void Calculate_Probability_Distribution_Face_Random_Data(int Percentages, int list[])
{
  int per = this.data.LengthOfTrainingLables_Face * Percentages;
  per = per/100;
  for(int i=0; i<per; i++)
  {
    for(int j=0; j<70*60; j++)
    {
      if(this.data.TrainingFace[list[i]][j] == '+' ||this.data.TrainingFace[list[i]][j] == '#')
      {
        if(this.data.TrainingFaceLabels[list[i]] == true)
        {
          this.ProbabilityDistribution_Face[0][j] = this.ProbabilityDistribution_Face[0][j] +1;
        }
        else
        {
          this.ProbabilityDistribution_Face[1][j] = this.ProbabilityDistribution_Face[1][j]+1;
        }
      }
    }
  }

  double temp = 0;
  for(int m =0; m<2; m++)
  {
    for(int k =0; k<70*60; k++)
    {
      temp = this.ProbabilityDistribution_Face[m][k];
      this.ProbabilityDistribution_Face[m][k] = temp/this.Number_of_faces;
    }
  }
}

public double[] Train_And_Test()
{
  double []Return_Array = new double[40];
  int Percentages =10;
  long startTime;
  long endTime;
  long timeElapsed;
  for(int i =0; i<10;i++)
  {
    this.Digit_constructor();
    startTime = System.nanoTime();
    this.NaiveBayes_Train_Digits(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Testing();
    Return_Array[i] = this.get_Digit_Accuracy();
    Return_Array[i+10] = (double)timeElapsed/1000000;

//Faces:
    this.Face_constructor();
    startTime = System.nanoTime();
    this.NaiveBayes_Train_Faces(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Testing_Face();
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
    this.Digit_constructor();
    startTime = System.nanoTime();
    this.Prediction_Train_Digit(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Testing();
    Return_Array[i] = this.get_Digit_Accuracy();
    Return_Array[i+10] = (double)timeElapsed/1000000;

//Faces:
    this.Face_constructor();
    startTime = System.nanoTime();
    this.Prediction_Train_Face(Percentages);
    endTime = System.nanoTime();
    timeElapsed = endTime - startTime;
    this.Testing_Face();
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
      this.Digit_constructor();
      this.Prediction_Train_Digit(Percentages);
      this.Testing();
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
      this.Face_constructor();
      this.Prediction_Train_Face(Percentages);
      this.Testing_Face();
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
