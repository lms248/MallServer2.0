/**
 * Excel（CSV）处理工具
 */

	function isIE() { //ie?
     if (!!window.ActiveXObject || "ActiveXObject" in window)
         return true;
     else
         return false;
 }
 
 function getXlsFromTbl(inTblId, inWindow) {
	
	 try {
         var allStr = "";
         var curStr = "";
         var fileName = getExcelFileName();
         //                var cc = document.all;
         //                var ccc = typeof (document.all)
         //                alert(cc)
         //                alert(ccc)
         if (inTblId != null && inTblId != "" && inTblId != "null") {
             var hh = isIE();
             if (hh == true) { //IE浏览器
                 curStr = getTblData(inTblId, inWindow);
                 if (curStr != null) {
                     allStr += curStr;
                 }
                 else {
                     alert("你要导出的表不存在！");
                     return;
                 }
                 doFileExport(fileName, allStr);
             } else {
                 curStr = getTblData1(inTblId, inWindow);
                 if (curStr != null) {
                     allStr += curStr;
                 }
                 else {
                     alert("你要导出的表不存在！");
                     return;
                 }
                 var uri = 'data:text/xls;charset=utf-8,\ufeff' + encodeURIComponent(allStr);
                 //创建a标签模拟点击下载
                 var downloadLink = document.createElement("a");
                 downloadLink.href = uri;
                 downloadLink.download = fileName;
                 document.body.appendChild(downloadLink);
                 downloadLink.click();
                 document.body.removeChild(downloadLink);
             }
         }
     }
     catch (e) {
         alert("导出发生异常:" + e.name + "->" + e.description + "!");
     }
}

 function getTblData(inTbl, inWindow) {
	//alert("导出。。。。。。。IE");
    var rows = 0;
    var tblDocument = document;
    if (!!inWindow && inWindow != "") {

        if (!document.all(inWindow)) {
            return null;
        }
        else {
            tblDocument = eval(inWindow).document;
        }
    }

    var curTbl = tblDocument.getElementById(inTbl);
    var outStr = "";
       
    if (curTbl != null) {
       
    	for (var j = 0; j <curTbl.rows.length; j++) {
       			if(j==0)
       			{		
       				for (var i = 0; i < curTbl.rows[0].cells.length; i++) {
       					
           				
       	                if (i == 0 && rows > 0) {
       	                    outStr += "\t";//\t
       	                    rows -= 1;
       	                }
       	                var s = curTbl.rows[0].cells[i].innerText;
       	                s = s.replace(",","");

       	                outStr += s + "\t";//\t
       	               
       	                if (curTbl.rows[j].cells[i].colSpan > 1) {
       	                    for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
       	                        outStr += "\t";//\t
       	                    }
       	                }
       	                if (i == 0) {
       	                    if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
       	                        rows = curTbl.rows[j].cells[i].rowSpan - 1;
       	                    }
       	                } 
       	            }
       		 outStr += "\r\n";
        	}
       		if(($("#c"+j+"").prop('checked')==true)){
       			for (var i = 0; i < curTbl.rows[j].cells.length; i++) {
       		  
				
       				
                if (i == 0 && rows > 0) {
                    outStr += "\t";//\t
                    rows -= 1;
                }
                var s = curTbl.rows[j+1].cells[i].innerText;
                s = s.replace(",","");

                outStr += s + "\t";//\t
               
                if (curTbl.rows[j].cells[i].colSpan > 1) {
                    for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
                        outStr += "\t";//\t
                    }
                }
                if (i == 0) {
                    if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
                        rows = curTbl.rows[j].cells[i].rowSpan - 1;
                    }
                } 
            }//
       	 
            outStr += "\r\n";
        	}
        }
        
    }

    else {
        outStr = null;
        alert(inTbl + "不存在 !");
    }
    return outStr;
}


function getTblData1(inTbl, inWindow) {
	//alert("导出。。。。。。。非IE");
    var rows = 0;
    var tblDocument = document;
    if (!!inWindow && inWindow != "") {
        if (!document.all(inWindow)) {
            return null;
        }
        else {
            tblDocument = eval(inWindow).document;
        }
    }

    var curTbl = tblDocument.getElementById(inTbl);
    var outStr = "";
       
    if (curTbl != null) {
    	for (var j = 0; j <curTbl.rows.length-1; j++) {
       		if(j==0) {		
       			for (var i = 0; i < curTbl.rows[0].cells.length; i++) {
   	                if (i == 0 && rows > 0) {
   	                    outStr += ",";//\t
   	                    rows -= 1;
   	                }
   	                var s = curTbl.rows[0].cells[i].innerText;
   	                s = s.replace(",","");

   	                outStr += s + ",";//\t
   	               
   	                if (curTbl.rows[j].cells[i].colSpan > 1) {
   	                    for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
   	                        outStr += ",";//\t
   	                    }
   	                }
   	                if (i == 0) {
   	                    if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
   	                        rows = curTbl.rows[j].cells[i].rowSpan - 1;
   	                    }
   	                } 
   	            }
       			outStr += "\r\n";
        	}
       		if(document.getElementsByName("content")[j].checked){
       			for (var i = 0; i < curTbl.rows[j].cells.length; i++) {
       				if (i == 0 && rows > 0) {
                        outStr += ",";//\t
                        rows -= 1;
                    }
                    var s = curTbl.rows[j+1].cells[i].innerText;
                    s = s.replace(",","");

                    outStr += s + ",";//\t
                   
                    if (curTbl.rows[j].cells[i].colSpan > 1) {
                        for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
                            outStr += ",";//\t
                        }
                    }
                    if (i == 0) {
                        if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
                            rows = curTbl.rows[j].cells[i].rowSpan - 1;
                        }
                    }
       			}//
       	 
            outStr += "\r\n";
        	}
        }
        
    }

    else {
        outStr = null;
        alert(inTbl + "不存在 !");
    }
    return outStr;
}


function getExcelFileName() {
	//alert("导出。。。。。。。4");
    var d = new Date();
    var curYear = d.getYear();
    var curMonth = "" + (d.getMonth() + 1);
    var curDate = "" + d.getDate();
    var curHour = "" + d.getHours();
    var curMinute = "" + d.getMinutes();
    var curSecond = "" + d.getSeconds();
    if (curMonth.length == 1) {
        curMonth = "0" + curMonth;
    }

    if (curDate.length == 1) {
        curDate = "0" + curDate;
    }

    if (curHour.length == 1) {
        curHour = "0" + curHour;
    }

    if (curMinute.length == 1) {
        curMinute = "0" + curMinute;
    }

    if (curSecond.length == 1) {
        curSecond = "0" + curSecond;
    }
    var fileName = "table" + "_" + curYear + curMonth + curDate + "_"
            + curHour + curMinute + curSecond + ".csv";
    return fileName;

}

function doFileExport(inName, inStr) { 
	//alert("导出。。。。。。。5");
    var xlsWin = null;
    if (!!document.all("glbHideFrm")) {
        xlsWin = glbHideFrm;
    }
    else {
        var width = 6;
        var height = 4;
        var openPara = "left=" + (window.screen.width / 2 - width / 2)
                + ",top=" + (window.screen.height / 2 - height / 2)
                + ",scrollbars=no,width=" + width + ",height=" + height;
        xlsWin = window.open("", "_blank", openPara);
    }
    xlsWin.document.write(inStr);
    xlsWin.document.close();
    xlsWin.document.execCommand('Saveas', true, inName);
    xlsWin.close();

}
