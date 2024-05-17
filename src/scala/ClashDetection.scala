package scala

import scala.io.{BufferedSource, Source}



object ClashDetection{

    class ProgrammeS(val ptype: String, val name: String, val year:String, val term: String){
        var activities: List[String] = List[String]()
        var clash: List[String] = List[String]()

        def findClash(): Unit ={
            val terminfo: String = ptype+","+name+","+year+","+term+",Compulsory"
            val file: BufferedSource = Source.fromFile("module.csv")
            for (line <- file.getLines) {
                //val fileContents: List[String] = line.split(",").toList
                //check if line matches terminfo
                val contain = line.contains(terminfo)
                if (contain){ //if true
                    //append lines in front of the list if data matches
                  activities ::= line
                }
            }
            file.close
            for (i <- activities.indices) {
                //compares one activity with all the other activity in the list to check clashes
                val activity1 = activities(i)
                val activity1list = activity1.split(",").toList
                val day1 = activity1list(7)
                val startTime1 = activity1list(8).toInt
                val endTime1 = activity1list(10).toInt

                for (j <- i + 1 until activities.length) {
                    val activity2 = activities(j)
                    val activity2list = activity2.split(",").toList
                    val day2 = activity2list(7)
                    val startTime2 = activity2list(8).toInt
                    val endTime2 = activity2list(10).toInt

                    if (day1 == day2 && ((startTime2 >= startTime1 && startTime2 < endTime1) || (startTime1 >= startTime2 && startTime1 < endTime2)  )) {
                        clash ::= activity1+"  clashes with  "+activity2+"\n"
                        //stores clash info to the list when clash condition is true
                    }
                }
            }
        }
    }


}
