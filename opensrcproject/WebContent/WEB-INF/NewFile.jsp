<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%@ page import="opensrcproject.NaverAPI" %>


<html>
<head>
<meta charset="utf-8">
<title>Line_Controls_Chart</title>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery.min.js"></script>
<!-- google charts -->
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>

	<h4>���̹� ���� �˻��� Ʈ���� �׷���</h4>

	<div id="Line_Controls_Chart">
		<!-- ���� ��Ʈ ������ ���� -->
		<div id="lineChartArea" style="padding: 0px 20px 0px 0px;"></div>
		<!-- ��Ʈ�ѹٸ� ������ ���� -->
		<div id="controlsArea" style="padding: 0px 20px 0px 0px;"></div>
	</div>
	
	<form action="<%=request.getContextPath()%>/NewFile.jsp" method="post">
		�˻� Ű���� : <input type="text" name="one"> <input type="submit"
			value="�˻�">
	</form>
	<%
		String keyword = request.getParameter("one");
		NaverAPI t = new NaverAPI(keyword);
		Double[] ratio = new Double[244];////////////
		for (int i = 0; i < 244; i++) {//////////////
			if (i != 0 && (Double)t.getR().get(i) == 1.0)
				ratio[i] = ratio[i - 1];
			else
				ratio[i] = (Double) t.getR().get(i);
		}
	%>
	<b></b>
	<button id="button1" onclick="button1_click();">�׷���</button>

</body>

<script>

  var chartDrowFun = {
 
    chartDrow : function(){
        var chartData = '';
		var temp = new Array();
		// jsp�迭�� �޴´�.
		<%for ( int s=0 ; s<244; s++ ){%>/////////////////
		temp.push(<%=ratio[s]%>);
		<%}%>
		
        //��¥���� �����ϰ� �����ø� �� �κ� �����ϼ���.
        var chartDateformat     = 'yyyy��MM��dd��';
        //������Ʈ�� ���� ��
        var chartLineCount    = 10;
        //��Ʈ�ѷ� �� ��Ʈ�� ���� ��
        var controlLineCount    = 10;
 
 
        function drawDashboard() {
 
          var data = new google.visualization.DataTable();
          //�׷����� ǥ���� �÷� �߰�
          data.addColumn('datetime' , '��¥');
          data.addColumn('number'   , '�˻���');
 
          //�׷����� ǥ���� ������
          var dataRow = [];
 
          for(var i = 0; i <= 243; i++){ //������ ����///////////////////
           // var total   = temp[i];
 
            dataRow = [new Date('2019', '9', i+1 , '10'), temp[i]];//man, woman , total];
            data.addRow(dataRow);
          }
 
 
            var chart = new google.visualization.ChartWrapper({
              chartType   : 'LineChart',
              containerId : 'lineChartArea', //���� ��Ʈ ������ ����
              options     : {
                              isStacked   : 'percent',
                              focusTarget : 'category',
                              height          : 500,
                              width              : '100%',
                              legend          : { position: "top", textStyle: {fontSize: 13}},
                              pointSize        : 5,
                              tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
                              hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
                                                                  years : {format: ['yyyy��']},
                                                                  months: {format: ['MM��']},
                                                                  days  : {format: ['dd��']},
                                                                  hours : {format: ['HH��']}}
                                                                },textStyle: {fontSize:12}},
                vAxis              : {minValue: 100,viewWindow:{min:0},gridlines:{count:-1},textStyle:{fontSize:12}},
                animation        : {startup: true,duration: 1000,easing: 'in' },
                annotations    : {pattern: chartDateformat,
                                textStyle: {
                                fontSize: 15,
                                bold: true,
                                italic: true,
                                color: '#871b47',
                                auraColor: '#d799ae',
                                opacity: 0.8,
                                pattern: chartDateformat
                              }
                            }
              }
            });
 
            var control = new google.visualization.ControlWrapper({
              controlType: 'ChartRangeFilter',
              containerId: 'controlsArea',  //control bar�� ������ ����
              options: {
                  ui:{
                        chartType: 'LineChart',
                        chartOptions: {
                        chartArea: {'width': '60%','height' : 80},
                          hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
                            gridlines:{count:controlLineCount,units: {
                                  years : {format: ['yyyy��']},
                                  months: {format: ['MM��']},
                                  days  : {format: ['dd��']},
                                  hours : {format: ['HH��']}}
                            }}
                        }
                  },
                    filterColumnIndex: 0
                }
            });
 
            var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
            date_formatter.format(data, 0);
 
            var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Chart'));
            window.addEventListener('resize', function() { dashboard.draw(data); }, false); //ȭ�� ũ�⿡ ���� �׷��� ũ�� ����
            dashboard.bind([control], [chart]);
            dashboard.draw(data);
 
        }
          google.charts.setOnLoadCallback(drawDashboard);
 
      }
    }
  function button1_click() {
	  $(document).ready(function(){
		  google.charts.load('current', {'packages':['line','controls']});
		  chartDrowFun.chartDrow(); //chartDrow() ����
		});
}

  </script>
</html>
