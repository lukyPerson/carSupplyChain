<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>第一个 ECharts 实例</title>
    <!-- 引入 echarts.js -->
    <script src="https://cdn.staticfile.org/echarts/4.3.0/echarts.min.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div><a href="${pageContext.request.contextPath}/cargoods/statistic">统计订单数据</a></div>
<div><a href="${pageContext.request.contextPath}/cargoods/statisticAmt">统计金额数据</a></div>
<div id="main" style="width: 600px;height:400px;"></div>
<div id="main1" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '第一个 ECharts 实例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            // data: [5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20]
            data:[${list[0]},${list[1]},${list[2]},${list[3]},${list[4]},${list[5]},${list[6]},${list[7]},${list[8]},${list[9]},${list[10]},${list[11]}]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);


    var myChart = echarts.init(document.getElementById('main1'));
    option = {
        title : {
            text: '某站点用户访问来源',       //大标题
            subtext: '纯属虚构',                //类似于副标题
            x:'center'                 //标题位置   居中
        },
        tooltip : {
            trigger: 'item',           //数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
            formatter: "{a} <br/>{b} : {c} ({d}%)"   //{a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）用于鼠标悬浮时对应的显示格式和内容
        },
        legend: {                           //图例组件。
            orient: 'vertical',             //图例列表的布局朝向
            left: 'left',
            data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
        },
        series : [              //系列列表。每个系列通过 type 决定自己的图表类型
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:${list[0]}, name:'1月'},
                    {value:${list[1]}, name:'2月'},
                    {value:${list[2]}, name:'3月'},
                    {value:${list[3]}, name:'4月'},
                    {value:${list[4]}, name:'5月'},
                    {value:${list[5]}, name:'6月'},
                    {value:${list[6]}, name:'7月'},
                    {value:${list[7]}, name:'8月'},
                    {value:${list[8]}, name:'9月'},
                    {value:${list[9]}, name:'10月'},
                    {value:${list[10]}, name:'11月'},
                    {value:${list[11]}, name:'12月'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
</script>
</body>
</html>