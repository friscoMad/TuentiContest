package tuenti.contest;
import java.util.*;
public class Freqs {
  String content; //contains initial string
  LinkedList<String[]> charLib = new LinkedList<String[]>(); //initial unordered frequency table
  LinkedList<String[]> finalTable = new LinkedList<String[]>(); //final ordered (highest to lowest) frequency table

  //constructor
  public Freqs(String text) {
    content = text;
  } //end constructor

  //adds characters to charLib if they are not already in there,
  //if the character is already in charLib then the frequency count for that character is incremented.
  public void countChars() {
    String tempStr = "";
    for(int i = 0;i < content.length();i++) {
      String[] curStr = new String[2];
      //if character does not exist in character library
      if(!charCheck(content.charAt(i))) {
        curStr[0] = Character.toString(content.charAt(i));
        curStr[1] = "1";
        charLib.add(curStr);
      } //end if
      //if character does exist in character library
      else {
        //loop through library for character
        for(int j = 0;j < charLib.size();j++) {
          //if chars match
          if(Character.toString(content.charAt(i)).equals(charLib.get(j)[0])) {
            //add to counter
            int tempInt = 0;
            tempInt = Integer.parseInt(charLib.get(j)[1].trim()) + 1;
            tempStr = Integer.toString(tempInt);
            curStr[0] = Character.toString(content.charAt(i));
            curStr[1] = tempStr;
            charLib.set(j, curStr);
            break; //break out of loop because we made a char match
          } //end if
        } //end for charLib.size()
      } //end else
      //System.out.println(content.charAt(i));
    } //end content for loop
    this.orderTable();
  } //end countChars

  //re-orders charLib into finalTable (highest to lowest character frequency)
  public void orderTable() {
    finalTable.addFirst(charLib.getFirst());
    for(int i = 1;i < charLib.size();i++) {
      //must initialize arrays on each run
      String[] tempArray = new String[2];
      int[] tempInt = new int[3];
      //translate array from charLib to tempArray
      tempArray[0] = charLib.get(i)[0];
      tempArray[1] = charLib.get(i)[1];
      //convert to int's for comparision purposes
      tempInt[0] = Integer.parseInt(tempArray[1].trim());
      tempInt[1] = Integer.parseInt(finalTable.getFirst()[1].trim());
      tempInt[2] = Integer.parseInt(finalTable.getLast()[1].trim());
      //if current char count is >= highest count
      if(tempInt[0] >= tempInt[1]) {
        finalTable.addFirst(tempArray);
      }
      else if(tempInt[0] <= tempInt[2]) {
        finalTable.addLast(tempArray);
      }
      else {
        finalTable.add(findIndex(tempInt[0]), tempArray);
      }
    }
  } //end orderTable

  //Helps in ordering charLib
  public int findIndex(int cur) {
    int index = 0;
    for(int i = 0;i < finalTable.size();i++) {
      int tempInt = Integer.parseInt(finalTable.get(i)[1].trim());
      if(cur >= tempInt) {
        index = i;
        break; //end loop
      }
    }
    return index;
  } //end findIndex

  //checks to see if the given character already exists in the charLib
  public boolean charCheck(char ch) {
    boolean check = false;
    char tempCh;
    for(int i = 0;i < charLib.size();i++) {
      tempCh = charLib.get(i)[0].charAt(0);
      if(tempCh == ch) {
        check = true;
      }
    }
    return check;
  } //end charCheck

  //returns initial string
  public String getStr() {
    return content;
  } //end getStr

  //returns size of frequency table (LinkedList<String[]>)
  public int getSize() {
    return finalTable.size();
  } //end getSize

  //returns the ordered frequency table
  public LinkedList<String[]> getList() {
    return finalTable;
  } //end getList

  //prints the final ordered frequency table
  public void printTable() {
    for(int j = 0;j < finalTable.size();j++) {
      System.out.println("Char: " + finalTable.get(j)[0] + "   Count: " + finalTable.get(j)[1]);
    }
  } //end printTable

  //returns the string representation of the final ordered frequency table
  public String returnTable() {
    String table = "";
    for(int j = 0;j < finalTable.size();j++) {
      table += "Char: " + finalTable.get(j)[0] + "   Count: " + finalTable.get(j)[1] + "\r\n";
    }
    return table;
  } //end returnTable

  //get character at specified index
  public char getChar(int index) {
    return finalTable.get(index)[0].charAt(0);
  } //end getChar

  //get frequency of character at specified index
  public int getFreq(int index) {
    return Integer.parseInt(finalTable.get(index)[1].trim());
  } //end getFreq

  //get the frequency of the specified character
  public int getCount(char ch) {
    int count = 0;
    //loop through library for character
    for(int j = 0;j < charLib.size();j++) {
      //if chars match
      if(ch == charLib.get(j)[0].charAt(0)) {
        count = Integer.parseInt(charLib.get(j)[1]);
        break; //break out of loop because we made a char match
      } //end if
    } //end for charLib.size()
    return count;
  } //end getCount

} //end Freqs
