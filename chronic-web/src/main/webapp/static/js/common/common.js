/**
 * 弹窗最大化
 * 
 * @param layer
 * @param title
 * @param url
 */
function alertByFull(layer, title, url) {
	layer.open({
		title : title,
		type : 2,
		maxmin: true, //开启最大化最小化按钮
		area: ['893px', '600px'],
		content : url,
		success : function(layero, index) {
			setTimeout(function() {
				layui.layer.tips('点击这里返回',
						'.layui-layer-setwin .layui-layer-close', {
							tips : [3, '#3595CC'],
							time: 1000
						});
			}, loadingTime)
		}
	});
}

/**
 * 判断是否为空
 * 
 * @param data
 * @returns {boolean}
 */
function checkIsNotNull(data) {
	return data != "" && data != undefined && data != null;
}

/**
 * 全选关联
 * 
 * @param form
 * @param all_delete_label
 * @param delete_label
 * @param allDeleteLabel
 * @param deleteLabel
 */
function checkIsAllCheck(form, all_delete_label, delete_label, allDeleteLabel,
		deleteLabel) {
	form.on('checkbox(' + all_delete_label + ')', function(data) { // 监听标题行checkbox
		var child = $(data.elem).parents('table').find(
				'tbody input[name=' + deleteLabel + ']'); // 找到tbody所有的行
		child.each(function(index, item) { // 遍历tbody所有的行
			item.checked = data.elem.checked; // 获取被选中checkbox
		});
		form.render('checkbox'); // 更新渲染
	});
	form.on('checkbox(' + delete_label + ')', function(data) { // 监听内容行checkbox
		var child = $(data.elem).parents('table').find(
				'tbody input[name=' + deleteLabel + ']');
		var allChecked = true;
		child.each(function(index, item) {
			if (!item.checked) {
				allChecked = false;
			}
		});
		$('thead input[name=' + allDeleteLabel + ']')[0].checked = allChecked;
		form.render('checkbox');
	});
}

/*/!**
 * 删除
 * 
 * @param deleteLabel
 * @param url
 * @param fun
 *!/
function doDelete(layer, deleteLabel, url, fun) {
	var countsCheckBox = $("input[name=" + deleteLabel + "]:checked");
	var userid = [];
	for (var i = 0; i < countsCheckBox.length; i++) {
		alluser_id = {};
		var useriddata = countsCheckBox[i].parentNode.parentNode.children[1].innerHTML;
		alluser_id['userid'] = useriddata;
		userid[i] = alluser_id;
	}
	if (userid.length == 0) {
		layer.msg('请选择删除项', function() {
		});
	} else {
		$.post(url, {
			userid : JSON.stringify(userid)
		}, function(data) {
			var backdata = JSON.parse(data);
			if (backdata.success) {
				layer.msg('删除成功!', {
					icon : 1,
				});
			} else {
				layer.msg('删除失败!', {
					icon : 2,
				});
			}
			fun;
		}, "json");
	}
}*/

/**
 * 考试报名
 * 
 * @param deleteLabel
 * @param url
 * @param fun
 */
function doApply(layer, deleteLabel, url, fun) {
	var countsCheckBox = $("input[name=" + deleteLabel + "]:checked");
	var id = countsCheckBox[0].parentNode.parentNode.children[1].innerHTML;
	var examstarttime = countsCheckBox[0].parentNode.parentNode.children[3].innerHTML;
	var examendtime = countsCheckBox[0].parentNode.parentNode.children[4].innerHTML;
	var examlimitnum = countsCheckBox[0].parentNode.parentNode.children[10].innerHTML;

	if (countsCheckBox.length > 1) {
		layer.msg('对不起，你不能选择多科同时报名', function() {
		});
	} else {
		$.post(url, {
			id : id,
			examstarttime : examstarttime,
			examendtime : examendtime,
			examlimitnum : examlimitnum
		}, function(data) {
			var backdata = JSON.parse(data);
			if (backdata.success == 403) {
				layer.msg('现在不是报名时间！', {
					icon : 5
				});
			} else if (backdata.success == 404) {
				layer.msg('没有剩余名额了！', {
					icon : 5
				});
			} else if (backdata.success == 500) {
				layer.msg('报名失败,请上传你的证件照！', {
					icon : 5
				});
			} else if (backdata.success == 400) {
				layer.msg('报名失败,你已经报名过该科目！', {
					icon : 5
				});
			} else if (backdata.success == 200) {
				layer.msg('报名成功,请等待管理员审核!', {
					icon : 6
				});
			} else if (backdata.success == 100) {
				layer.msg('报名失败,请稍后重试！', {
					icon : 5
				});
			}
			fun;
		}, "json");
	}
}

/**
 * 打印准考证
 * 
 * @param deleteLabel
 * @param url
 * @param fun
 */
function doPrintTicket(layer, deleteLabel, url, fun) {
	var countsCheckBox = $("input[name=" + deleteLabel + "]:checked");
	var id = countsCheckBox[0].parentNode.parentNode.children[1].innerHTML;
	var examstartprint = countsCheckBox[0].parentNode.parentNode.children[4].innerHTML;
	var examendprint = countsCheckBox[0].parentNode.parentNode.children[5].innerHTML;
	var applystate = countsCheckBox[0].parentNode.parentNode.children[9].innerHTML;

	if (countsCheckBox.length > 1) {
		layer.msg('对不起，你不能选择多科同时打印', function() {
		});
	} else {
		$.post(url, {
			examstartprint : examstartprint,
			examendprint : examendprint,
			applystate : applystate
		}, function(data) {
			var backdata = JSON.parse(data);
			if (backdata.success == 400) {
				layer.msg('报名待审核！', {
					icon : 5
				});
			} else if (backdata.success == 403) {
				layer.msg('审核未通过！', {
					icon : 5
				});
			} else if (backdata.success == 404) {
				layer.msg('现在不是打印准考证时间！', {
					icon : 5
				});
			} else {
				alertByFull(layer, "打印准考证",
						"http://www.chronic.com/addPrintTicketApplyPrint?id=" + id);
			}
			fun;
		}, "json");

	}
}

/**
 * 删除报名
 * 
 * @param deleteLabel
 * @param url
 * @param fun
 */
function doDeleteApply(layer, deleteLabel, url, fun) {
	var countsCheckBox = $("input[name=" + deleteLabel + "]:checked");
	var id = countsCheckBox[0].parentNode.parentNode.children[1].innerHTML;
	var applystate = countsCheckBox[0].parentNode.parentNode.children[9].innerHTML;

	if (countsCheckBox.length > 1) {
		layer.msg('对不起，你不能选择多科同时删除', function() {
		});
	} else {
		$.post(url, {
			id : id,
			applystate : applystate
		}, function(data) {
			var backdata = JSON.parse(data);
			if (backdata.success == 400) {
				layer.msg('已通过审核，不能删除！', {
					icon : 5
				});
			} else if (backdata.success) {
				layer.msg('删除成功!', {
					icon : 1,
				});
			} else {
				layer.msg('删除失败!', {
					icon : 2,
				});
			}
			fun;
		}, "json");

	}
}

/**
 * 分页
 * @param laypage
 * @param page_label
 * @param count
 * @param curr
 * @param limit
 * @param fun
 * @returns
 * [ 'prev', 'page', 'next', 'count', 'limit', 'skip'],
 */
function renderPage(laypage, page_label, count, curr, limit, fun) {
	laypage.render({
		elem : page_label,
		count : count,
		curr : curr,
		limit:limit,
		layout : ['prev', 'page', 'next', 'skip','count'],
		jump : function(obj, first) {
			if (!first) {
				fun(obj.curr); // 传递当前页
			}
		}
	});
}

/**
 * 全选、全不选
 * @param check_all_btn
 * @param check_btn
 * @returns
 */
function checkall_reverse(check_all_btn,check_btn){
	check_all_btn.click(function(){
		//如果是原生的属性，使用prop获取比较好
		check_btn.prop("checked",$(this).prop("checked"))
	});
	check_btn.click(function(){
		//当check_btn点满以后check_all_btn勾上，否则不选中
		//获取被选中的checkbtn个数
		var flag = check_btn.filter(":checked").length == check_btn.length;
		check_all_btn.prop("checked",flag);
	});
}

/**
 * 格式化时间
 */
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}


/**
 * 删除
 * @param layer
 * @param checkids
 * @param url
 * @param fun
 * @returns
 */
function doDelete(layer, checkids, url, fun) {
    if (checkids.length === 0) {
        layer.msg("请选择删除项");
    } else {
        $.ajax({
            url: url,
            type: "post",
            traditional: true,
            data: {
            	checkids: checkids
            },
            dataType: "json",
            success: function (data) {
            	if(data.code === code_success){
            		layer.msg(data.msg, {
            			icon : 1,
            			time: loadingTime5h
            		});
            	}else{
            		layer.msg(data.msg, {
    					icon : 2,
    					time: loadingTime1k
    				});
            	}
                fun;
            }
        });
    }
}


