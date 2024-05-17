import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileReader


data class Programme(var ptype: String, var name: String) {
    var programmeType: String = ptype
    var programmeName: String = name

    var line = "$programmeType,$programmeName\n"

    fun writeToFile(){
        try{
            //write line to the end of file
            val pfile = FileWriter("programme.csv",true)
            pfile.write(line)
            pfile.close()

        }catch (ex:Exception){
            println(ex.message)
        }
    }

    fun deleteLine(){
        try {
            val plist = mutableListOf<String>()
            val filter = "$programmeType,$programmeName"

            val pfile = BufferedReader(
                FileReader("programme.csv")
            )
            var s: String?
            while (pfile.readLine().also { s = it } != null) {
                plist.add(s.toString())
            }

            val newList = plist.filter { actor -> actor.contains(filter) }
            //search the matching lines and removes from list
            for (item in newList) {
                plist.remove(item)
            }

            pfile.close()


            val pfile2 = FileWriter("programme.csv")
            //updates the new list to file
            for (item in plist) pfile2.write(item+"\n")
            pfile2.close()


        }catch (ex:Exception){
            println(ex.message)
        }

    }
}

open class Module(var tname: String, var pname: String, var pyear: String, var term: String, var mtype: String,
                  var mname: String,var aname: String, var dname: String, var time: Int, var length: Int){
    var programmeType: String = tname
    var programmeName: String = pname
    var yearOfstudy: String = pyear
    var termOfstudy: String = term
    var moduleType: String = mtype
    var moduleName: String = mname
    var activityName: String = aname
    var weekday: String = dname
    var startingTime: Int = time
    var duration: Int = length

    val endTime: Int = startingTime + duration

    var timetableList = mutableListOf<String>()

    var postgraduateCheck: Boolean = true
    var compulsoryCheck: Boolean = true
    var timeCheck: Boolean = true
    var passWrite: Boolean = false

    var line = "$programmeType,$programmeName,$yearOfstudy,$termOfstudy,$moduleType,$moduleName,$activityName,$weekday,$startingTime,$duration,$endTime\n"

    fun writeToFile(){
        try {
            searchFile()
            //search through timetable to check no. of compulsory item
            var noCompulsory = 0
            for (item in timetableList){
                if (item.contains("Compulsory")){
                    noCompulsory += 1
                }
            }

            if ((programmeType == "Postgraduate" && yearOfstudy != "Year 1") || (noCompulsory == 4) || endTime > 21) {
                if (programmeType == "Postgraduate" && yearOfstudy != "Year 1") {
                    //checks postgraduate's year of study, can only be Year 1
                    postgraduateCheck = false
                }
                if (noCompulsory == 4) {
                    //checks for no of compulsory modules, max of 4
                    compulsoryCheck = false
                }
                if (endTime > 21) {
                    //checks finishing time, can't be over 21:00
                    timeCheck = false
                }
            }
            else {
                passWrite = true
                val mfile = FileWriter("module.csv", true)
                //write date into file
                mfile.write(line)
                mfile.close()
            }
        }catch (ex:Exception){
            println(ex.message)
        }
    }

    fun deleteLine(){
        try {
            val mlist = mutableListOf<String>()
            val filter = "$programmeType,$programmeName,$yearOfstudy,$termOfstudy,$moduleType,$moduleName,$activityName"

            val mfile = BufferedReader(
                FileReader("module.csv")
            )
            var s: String?
            while (mfile.readLine().also { s = it } != null) {
                mlist.add(s.toString())
            }

            val newList = mlist.filter { actor -> actor.contains(filter) }
            //find matching data and removes from the list
            for (item in newList) {
                mlist.remove(item)
            }
            mfile.close()


            val mfile2 = FileWriter("module.csv")
            //updates file with new list
            for (item in mlist) mfile2.write(item+"\n")
            mfile2.close()


        }catch (ex:Exception){
            println(ex.message)
        }

    }

    fun searchFile() {
        try {
            val mlist = mutableListOf<String>()
            var filter = "$programmeType,$programmeName,$yearOfstudy,$termOfstudy"
            if(moduleType == "Compulsory Modules Only"){
                //happens when searching for clashes as only compulsory activity matters
                filter += ",Compulsory"
            }
            val mfile = BufferedReader(
                FileReader("module.csv")
            )
            var s: String?
            while (mfile.readLine().also { s = it } != null) {
                mlist.add(s.toString())
            }

            val newList = mlist.filter { actor -> actor.contains(filter) }
            //searching for matching activities and adds to timetable list
            for (item in newList) {
                timetableList.add(item)
            }


            mfile.close()

        }catch (ex:Exception){
            println(ex.message)
        }

    }

    fun ovlp(input: List<String?>): MutableList<String?> {
        val resultList: MutableList<String?> = ArrayList()

        for (i in input.indices) {
            //compares one activity with all the other activity in the list to check clashes
            val d1 = input[i]
            val d1Arr = d1!!.split(",".toRegex()).toTypedArray()
            val length = d1Arr.size
            val daya = d1Arr[length - 4]
            val starta = Integer.valueOf(d1Arr[length - 3])
            val enda = Integer.valueOf(d1Arr[length - 1])
            for (j in i + 1 until input.size) {
                val d2 = input[j]
                val d2Arr = d2!!.split(",".toRegex()).toTypedArray()
                val dayb = d2Arr[length - 4]
                val startb = Integer.valueOf(d2Arr[length - 3])
                val endb = Integer.valueOf(d2Arr[length - 1])
                if (daya == dayb && (startb in starta until enda || starta in startb until endb)) {
                    //stores clash info to the list when clash condition is true
                    resultList.add("$d1  clashes with  $d2")
                }
            }
        }
        return resultList

    }

    //use this to sort out the timetable list in order
    fun sort() {
        val list: MutableList<Info> = java.util.ArrayList()
        for (item in timetableList) {
            if (item.trim { it <= ' ' }.isEmpty()) continue
            list.add(Info(item))
        }
        //sorting timetable list
        list.sortWith(java.util.Comparator { o1, o2 ->
            if (o1.day == o2.day) {
                o1.start - o2.start
            } else o1.day - o2.day
        })
        timetableList = mutableListOf<String>()
        for (i in list) {
            timetableList.add(i.content)
            //updates timetable list with new sorted list
        }
    }

    internal class Info(var content: String) {
        var day: Int
        var start: Int

        companion object {
            fun getDay(d: String?): Int {
                when (d) {
                    //apply days in week with numbers for sorting
                    "Monday" -> return 1
                    "Tuesday" -> return 2
                    "Wednesday" -> return 3
                    "Thursday" -> return 4
                    "Friday" -> return 5
                }
                return 0
            }
        }

        init {
            val infos = content.split(",".toRegex()).toTypedArray()
            //select time info out for sorting
            start = Integer.valueOf(infos[infos.size - 3])
            day = getDay(infos[infos.size - 4])
        }
    }


}



