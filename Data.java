/*
  This class reads from given filename and saves data from the txt file to array of arrays.
  Assuming that the each image is same size which is 28X28 for  digits and  70X60 for face
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;



public class Data{

//DigitData:
  public char [][]TrainingDigits;
  public int []TrainingDigitLabels;
  public int LengthOfTrainingLables;

  public char [][]TestData;
  public int[]TestLables;
  public int LengthofTestLables;

  public char [][]ValidationData;
  public int []ValidationLables;
  public int LengthofValidationLables;

//Face Data:
  public char [][]TrainingFace;
  public Boolean []TrainingFaceLabels;
  public int LengthOfTrainingLables_Face;

  public char [][]TestData_Face;
  public Boolean[]TestLables_Face;
  public int LengthofTestLables_Face;

  public char [][]ValidationData_Face;
  public Boolean []ValidationLables_Face;
  public int LengthofValidationLables_Face;


public Data(String Data_filename, String Lable_Filename, String TestingData, String TestingLables, String Validation_D, String ValidationLables_D,
            String Face_training, String Face_Training_Lable, String Face_test_Data, String Face_Test_Lable, String Validation_F, String ValidationLables_F)
{

int Total_Lines;
int Total_Test_Lines;

//Digit Data:
try{
Total_Lines  = this.Return_Lines(Data_filename);
this.LengthOfTrainingLables = Total_Lines/28;
this.TrainingDigits = new char[Total_Lines/28][28*28];
this.TrainingDigitLabels = new int[Total_Lines/28];

Total_Test_Lines = this.Return_Lines(TestingData);
this.LengthofTestLables = Total_Test_Lines/28;
this.TestData = new char[this.LengthofTestLables][28*28];
this.TestLables = new int[this.LengthofTestLables];

int Total_Validation_Lines = this.Return_Lines(Validation_D);
this.LengthofValidationLables = Total_Validation_Lines/28;
this.ValidationData = new char[this.LengthofValidationLables][28*28];
this.ValidationLables = new int[this.LengthofValidationLables];


this.Read_And_Save_Digit_Data(Data_filename, Total_Lines);
this.Read_And_Save_Digit_Lables(Lable_Filename, Total_Lines);

this.Read_And_Save_Digit_Test_Data(TestingData, Total_Test_Lines);
this.Read_And_Save_Digit_Test_Lables(TestingLables, Total_Test_Lines);

this.Read_And_Save_Digit_Validation_Data(Validation_D, Total_Validation_Lines);
this.Read_And_Save_Digit_Validation_Lables(ValidationLables_D, Total_Validation_Lines);

}catch (FileNotFoundException e)
{
  System.out.println("Some or all Digit Files not found");
}

//Face Data:
try{
int Total_Lines_Face_t  = this.Return_Lines(Face_training);
this.LengthOfTrainingLables_Face = Total_Lines_Face_t/70;
this.TrainingFace = new char[Total_Lines_Face_t/70][70*60];
this.TrainingFaceLabels = new Boolean[Total_Lines_Face_t/70];

this.Read_And_Save_Face_train_Data(Face_training, Total_Lines_Face_t);
this.Read_And_Save_Face_train_Lables(Face_Training_Lable, this.LengthOfTrainingLables_Face);

int Total_Lines_Face_test = this.Return_Lines(Face_test_Data);
this.LengthofTestLables_Face = Total_Lines_Face_test/70;
this.TestData_Face = new char[this.LengthofTestLables_Face][70*60];
this.TestLables_Face = new Boolean[this.LengthofTestLables_Face];

this.Read_And_Save_Face_Test_Data(Face_test_Data, Total_Lines_Face_test);
this.Read_And_Save_Face_Test_Lables(Face_Test_Lable, this.LengthofTestLables_Face);

int Total_Lines_Face_Validation = this.Return_Lines(Validation_F);
this.LengthofValidationLables_Face = Total_Lines_Face_Validation/70;
this.ValidationData_Face = new char[this.LengthofValidationLables_Face][70*60];
this.ValidationLables_Face = new Boolean[this.LengthofValidationLables_Face];

this.Read_And_Save_Face_Validation_Data(Validation_F, Total_Lines_Face_Validation);
this.Read_And_Save_Face_Validation_Lables(ValidationLables_F, this.LengthofValidationLables_Face);

}catch (FileNotFoundException e)
{
  System.out.println("Some or all Face Files not found");
}

}


//Read data from given file name and save it as arrays of arrays:
//This function returns number of lines in given file:
public int Return_Lines(String Data_filename) throws FileNotFoundException
{
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);
  int c =0;
  while(sc.hasNextLine())
  {
    c = c +1;
    sc.nextLine();
  }
return c;
}

public void Read_And_Save_Digit_Data(String Data_filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/28; i++)
  {
    indicator =0;
    for(int j =0; j<28; j++)
    {
      Line = "";
      Line = sc.nextLine();
      Line.getChars(0,27,this.TrainingDigits[i],indicator);
      indicator = indicator + 28;
    }
  }
}

public void Read_And_Save_Digit_Lables(String Lable_Filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Lable_Filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/28; i++)
  {
    this.TrainingDigitLabels[i] = Character.getNumericValue(sc.next().charAt(0));
  }
}



public void Read_And_Save_Digit_Test_Data(String Data_filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/28; i++)
  {
    indicator =0;
    for(int j =0; j<28; j++)
    {
      Line = "";
      Line = sc.nextLine();
      Line.getChars(0,27,this.TestData[i],indicator);
      indicator = indicator + 28;
    }
  }
}

public void Read_And_Save_Digit_Test_Lables(String Lable_Filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Lable_Filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/28; i++)
  {
    this.TestLables[i] = Character.getNumericValue(sc.next().charAt(0));
  }
}


public void Read_And_Save_Digit_Validation_Data(String Data_filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/28; i++)
  {
    indicator =0;
    for(int j =0; j<28; j++)
    {
      Line = "";
      Line = sc.nextLine();
      Line.getChars(0,27,this.ValidationData[i],indicator);
      indicator = indicator + 28;
    }
  }
}

public void Read_And_Save_Digit_Validation_Lables(String Lable_Filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Lable_Filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/28; i++)
  {
    this.ValidationLables[i] = Character.getNumericValue(sc.next().charAt(0));
  }
}




public void Read_And_Save_Face_train_Data(String Data_filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines/70; i++)
  {
    indicator =0;
    for(int j =0; j<70; j++)
    {
      Line = "";
      Line = sc.nextLine();
      Line.getChars(0,59,this.TrainingFace[i],indicator);
      indicator = indicator + 60;
      //System.out.println(Line);
    }
  }
}

public void Read_And_Save_Face_train_Lables(String Lable_Filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Lable_Filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines; i++)
  {
    this.TrainingFaceLabels[i] = this.Convert_char_to_bool(sc.next().charAt(0));
  }
}

public Boolean Convert_char_to_bool(char c)
{
  if(c == '1')
  {
    return true;
  }
  return false;
}



public void Read_And_Save_Face_Test_Data(String Data_filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);
  String Line;

for(int i =0; i<Total_Lines/70; i++)
  {
    indicator =0;
    for(int j =0; j<70; j++)
    {
      Line = "";
      Line = sc.nextLine();
      Line.getChars(0,59,this.TestData_Face[i],indicator);
      indicator = indicator + 60;
    }
  }
}

public void Read_And_Save_Face_Test_Lables(String Lable_Filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Lable_Filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines; i++)
  {
    this.TestLables_Face[i] = this.Convert_char_to_bool(sc.next().charAt(0));
  }
}



public void Read_And_Save_Face_Validation_Data(String Data_filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Data_filename);
  Scanner sc = new Scanner(file);
  String Line;

for(int i =0; i<Total_Lines/70; i++)
  {
    indicator =0;
    for(int j =0; j<70; j++)
    {
      Line = "";
      Line = sc.nextLine();
      Line.getChars(0,59,this.ValidationData_Face[i],indicator);
      indicator = indicator + 60;
//      System.out.println(Line);
    }
  }
}

public void Read_And_Save_Face_Validation_Lables(String Lable_Filename, int Total_Lines) throws FileNotFoundException
{
  int indicator;
  File file = new File(Lable_Filename);
  Scanner sc = new Scanner(file);

  String Line;
  for(int i =0; i<Total_Lines; i++)
  {
    this.ValidationLables_Face[i] = this.Convert_char_to_bool(sc.next().charAt(0));
  }
}


//Healper Functions:
public int getRandomInt(int min, int max) {
    Random random = new Random();
    return random.nextInt((max - min) + 1) + min;
}

//This function creates array with size of Training data *Percentages/100, to compute the  average Prediction error.
public int[] Calculate_Random_Data_index(int Percentages, int maximum)
{
  int m = maximum* Percentages;
  m = m/100;
  int random;
  int last =-1;// This is the last added index for RandomIndex array
//System.out.println("maximum+ "+maximum);
  int []RandomIndex = new int[m];

  for(int i =0; i<m;i++)
  {
    RandomIndex[i] = -1;
  }

  int temp =-1;
  int n =0;
  while(n<m)
  {
    random = this.getRandomInt(0, maximum-1);
    //Check if this random number is already included in Random index list, if not add it, otherwise continue
    if(last == -1)
    {
      last = last +1;
      RandomIndex[last] = random;
      n = n+1;
    }
    else
    {
    for(int j =0; j<=last;j++)
    {
      if(RandomIndex[j] == random)
      {
        temp = j;
        break;
      }
    }

    if(temp == -1)
    {
      last = last+1;
      RandomIndex[last]= random;
      n = n+1;
    }

    temp = -1;
  }

  }

  return RandomIndex;
}

}
