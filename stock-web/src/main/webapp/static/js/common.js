$.ajaxSetup({
    async: true,
    global: false
});
var Container = function (isPagination, targetDivId, tableId) {
    var object = new Object;
    object.dataCache;
    object.headerHtml;
    object.isPagination = isPagination;
    object.targetDivId = targetDivId;
    object.pagination;
    object.tableId = tableId;
    object.headAarry;
    object.AjaxUrl;
    object.params = {};
    object.callbackFun;
    object.sortable = false;
    object.sorter = null;
    object.data;
    object.begin;
    object.end;
    object.tableClassName = 'tablestyle';
    object.setTableHeadInfo = null;
    object.init = function (headers) {
        object.headAarry = headers;
        if (headers == null || headers == undefined || headers.length == 0) {
            return
        };
        object.headerHtml = '<table id="' + object.tableId + '" class="' + object.tableClassName + '" >';
        object.headerHtml += '<tr id="' + object.tableId + '_head">';
        for (var i = 0; i < object.headAarry.length; i++) {
            var sortable = object.headAarry[i].sortable;
            var sorterImgHtml = "";
            var istarget = object.headAarry[i].istarget;
            if (sortable !== null && sortable !== undefined && sortable && object.sorter === null) {
                object.sortable = true;
                if (istarget === undefined || istarget === null) {
                    istarget = false
                };
                object.sorter = new sorter(object)
            };
            if (object.headAarry[i].sortable === true) {
                if (istarget) {
                    sorterImgHtml = object.sorter.getImgHtml(object.headAarry[i]);
                    object.sorter.params = {
                        'sortName': object.headAarry[i].code,
                        'direction': object.sorter.defaultDirection
                    };
                    object.headerHtml += '<th sortable="' + object.headAarry[i].sortable + '" index="' + object.headAarry[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headAarry[i].code + '" desc="' + this.headAarry[i].headName + '" istarget=' + object.headAarry[i].istarget + '><a href="#">' + this.headAarry[i].headName + sorterImgHtml + '</a></th>'
                };
                if (!istarget) {
                    object.headerHtml += '<th sortable="' + object.headAarry[i].sortable + '" index="' + object.headAarry[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headAarry[i].code + '" desc="' + this.headAarry[i].headName + '" istarget=' + object.headAarry[i].istarget + '><a href="#">' + this.headAarry[i].headName + '</a></th>'
                }
            } else {
                object.headerHtml += '<th sortable="' + object.headAarry[i].sortable + '" index="' + object.headAarry[i].index + '"id="' + object.headAarry[i].code + '" desc="' + this.headAarry[i].headName + '">' + this.headAarry[i].headName + '</th>'
            }
        };
        object.headerHtml += '</tr></table>';
        $("#" + object.targetDivId).append(object.headerHtml);
        if (object.sortable == true) {
            object.sorter.addEvent()
        }
    };
    object.init = function (headers, url, params) {
        object.AjaxUrl = url;
        object.params = params;
        object.headAarry = headers;
        object.headAarry = headers;
        if (headers == null || headers == undefined || headers.length == 0) {
            return
        };
        object.headerHtml = '<table id="' + object.tableId + '" class="' + object.tableClassName + '" >';
        object.headerHtml += '<tr id="' + object.tableId + '_head">';
        for (var i = 0; i < object.headAarry.length; i++) {
            var sortable = object.headAarry[i].sortable;
            var sorterImgHtml = "";
            var istarget = object.headAarry[i].istarget;
            if (sortable !== null && sortable !== undefined && sortable && object.sorter === null) {
                object.sortable = true;
                if (istarget === undefined || istarget === null) {
                    istarget = false
                };
                object.sorter = new sorter(object)
            };
            if (object.headAarry[i].sortable === true) {
                if (istarget) {
                    sorterImgHtml = object.sorter.getImgHtml(object.headAarry[i]);
                    object.sorter.params = {
                        'sortName': object.headAarry[i].code,
                        'direction': object.sorter.defaultDirection
                    };
                    object.headerHtml += '<th sortable="' + object.headAarry[i].sortable + '" index="' + object.headAarry[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headAarry[i].code + '" desc="' + this.headAarry[i].headName + '" istarget=' + object.headAarry[i].istarget + '><a href="#">' + this.headAarry[i].headName + sorterImgHtml + '</a></th>'
                };
                if (!istarget) {
                    object.headerHtml += '<th sortable="' + object.headAarry[i].sortable + '" index="' + object.headAarry[i].index + '" direction="' + object.sorter.defaultDirection + '" id="' + object.headAarry[i].code + '" desc="' + this.headAarry[i].headName + '" istarget=' + object.headAarry[i].istarget + '><a href="#">' + this.headAarry[i].headName + '</a></th>'
                }
            } else {
                object.headerHtml += '<th sortable="' + object.headAarry[i].sortable + '" index="' + object.headAarry[i].index + '"id="' + object.headAarry[i].code + '" desc="' + this.headAarry[i].headName + '">' + this.headAarry[i].headName + '</th>'
            }
        };
        object.headerHtml += '</tr></table>';
        $("#" + object.targetDivId).append(object.headerHtml);
        if (object.sortable == true) {
            object.sorter.addEvent()
        }
    };
    object.startloadingImg = function () {
        $("#zeroRecordDiv_" + object.targetDivId).remove();
        $("#" + object.targetDivId + " tr").remove(".data");
        $("#" + object.tableId).after("<div class='loading' id='loadingDiv_" + object.targetDivId + "'  style='display:none,align:center' width='100%' height='30'><img height='30' src='/images/loading.gif'></div>");
        $("#loadingDiv_" + object.targetDivId).attr('display', '')
    };
    object.stoploadingImg = function () {
        $("#loadingDiv_" + object.targetDivId).detach()
    };

    object.getDataByAjax = function (url, params, callbackFun) {
        object.AjaxUrl = url;
        object.params = object.mergeJson(object.params, params);
        object.callbackFun = callbackFun;
        var p = object.params;
        if (object.sortable == true) {
            p = object.mergeJson(object.params, object.sorter.params)
        };
        object.startloadingImg();
        var ajaxSuccessCallback = function (data) {
            if (!$.trim(data.success).length == 0) {
                if (data.success == "false") {
                    if ($.trim(data.errorType).length != 0) {
                        if ($.trim(data.errorType) == "IPtableException" || $.trim(data.errorType) == "IpCountException") {
                            if (language == "cn") window.location.href = "/ip.htm";
                            else if (language == "en") window.location.href = "/ip_en.htm"
                        } else if ($.trim(data.errorType) == "IllegalParametersException") {
                            if (language == "cn") alert("您输入的查询条件中含有非法字符.");
                            if (language == "en") alert("The Conditions You Input Contains Illegal Parameters.")
                        };
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    } else {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    };
                    object.stoploadingImg();
                    return
                }
            };
            object.stoploadingImg();
            object.dataCache = data.result;
            object.data = data;
            if ($.isEmptyObject(object.dataCache)) {
                object.dataCache = {}
            };
            if ($.isEmptyObject(object.data)) {
                object.data = {}
            };
            var pagination = new newPagination(object);
            if (Boolean(object.isPagination)) {
                object.pagination = pagination;
                pagination.pageHelp = data.pageHelp;
                pagination.init()
            };
            if (Boolean(object.isPagination)) {
                if (pagination.pageNo <= pagination.cacheSize) {
                    object.render(object.dataCache, ((pagination.pageNo - 1) * pagination.pageSize + 1), ((pagination.pageNo - 1) * pagination.pageSize + pagination.pageSize))
                } else {
                    var beginPageNo = pagination.getBeginPage(pagination.pageNo);
                    var localPageNo = (pagination.pageNo - beginPageNo) + 1;
                    object.render(object.dataCache, ((localPageNo - 1) * pagination.pageSize + 1), ((localPageNo - 1) * pagination.pageSize + pagination.pageSize))
                }
            } else {
                object.render(object.dataCache, 1, object.dataCache.length)
            }
        };
        var ajaxParam = {
            type: "get",
            url: url,
            data: p,
            dataType: "jsonp",
            jsonp: "jsonCallBack",
            jsonpCallback: "jsonpCallback" + Math.floor(Math.random() * (100000 + 1)),
            timeout: 30000,
            success: ajaxSuccessCallback,
            error: function (jqXHR, textStatus, errorThrown) {
                if (textStatus == "timeout") {
                    if (language == "cn") window.location.href = "/500.htm";
                    else if (language == "en") window.location.href = "/500_en.htm"
                } else {
                    if (language == "cn") window.location.href = "/500.htm";
                    else if (language == "en") window.location.href = "/500_en.htm"
                }
            }
        };
        $.ajax(ajaxParam)
    };

    object.render = function (result, begin, end) {
        if (end === undefined) {
            end = 0
        };
        object.begin = begin;
        object.end = end;
        $("#zeroRecordDiv_" + object.targetDivId).remove();
        var listHtml = "";
        $("#" + object.targetDivId + " tr").remove(".data");
        if ($.isEmptyObject(result)) {
            result.length = 0
        };
        if (end > result.length) {
            end = result.length
        };
        if (result.length === 0) {
            if (language == "cn") $("#" + object.tableId).after("<div id='zeroRecordDiv_" + object.targetDivId + "'>对不起! 共找到0条记录 </div>");
            else if (language == "en") $("#" + object.tableId).after("<div id='zeroRecordDiv_" + object.targetDivId + "'>Sorry! No data found. </div>")
        };
        var x = 1;
        for (var i = begin; i <= end; i++) {
            if (Math.round(i % 2) == 0) {
                listHtml += '<tr class="tr_even data">';
                for (var a = 0; a < object.headAarry.length; a++) {
                    var code = object.headAarry[a].code;
                    var formatter = object.headAarry[a].formatter;
                    var noWrap = object.headAarry[a].nowrap;
                    var valign = object.headAarry[a].valign;
                    var halign = object.headAarry[a].halign;
                    var className = object.headAarry[a].className;
                    var isEmptyClassName = $.isEmptyObject(className);
                    var isEmptyHalign = ($.trim(halign).length == 0);
                    if ((typeof formatter) == 'function') {
                        listHtml += '<td valign="' + $.trim(valign) + '" class="' + (isEmptyClassName ? "" : className) + (Boolean(noWrap) == true ? " nowrap " : " ") + (isEmptyHalign ? "" : halign) + '">' + formatter(result[(i - 1)], object.headAarry[a].code) + '</td>'
                    } else {
                        if (code === "defaultRank") {
                            listHtml += '<td valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "" : className) + (Boolean(noWrap) == true ? " nowrap " : " ") + (isEmptyHalign ? "" : halign) + '">' + x + '</td>'
                        } else {
                            listHtml += '<td  valign="' + $.trim(valign) + '" class="' + (isEmptyClassName ? "" : className) + (Boolean(noWrap) == true ? " nowrap " : " ") + (isEmptyHalign ? "" : halign) + '">' + eval('result[(i-1)].' + object.headAarry[a].code) + '</td>'
                        }
                    }
                };
                listHtml += '</tr>'
            } else {
                listHtml += '<tr class="tr_odd data">';
                for (var a = 0; a < object.headAarry.length; a++) {
                    var code = object.headAarry[a].code;
                    var formatter = object.headAarry[a].formatter;
                    var className = object.headAarry[a].className;
                    var noWrap = object.headAarry[a].nowrap;
                    var halign = object.headAarry[a].halign;
                    var valign = object.headAarry[a].valign;
                    var isEmptyClassName = $.isEmptyObject(className);
                    var isEmptyHalign = ($.trim(halign).length == 0);
                    if ((typeof formatter) == 'function') {
                        listHtml += '<td valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "" : className) + (Boolean(noWrap) == true ? " nowrap " : " ") + (isEmptyHalign ? "" : halign) + '">' + formatter(result[(i - 1)], object.headAarry[a].code) + '</td>'
                    } else {
                        if (code === "defaultRank") {
                            listHtml += '<td valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "" : className) + (Boolean(noWrap) == true ? " nowrap " : " ") + (isEmptyHalign ? "" : halign) + '">' + x + '</td>'
                        } else {
                            listHtml += '<td valign="' + $.trim(valign) + '"  class="' + (isEmptyClassName ? "" : className) + (Boolean(noWrap) == true ? " nowrap " : " ") + (isEmptyHalign ? "" : halign) + '">' + eval('result[(i-1)].' + object.headAarry[a].code) + '</td>'
                        }
                    }
                };
                listHtml += '</tr>'
            };
            x++
        };
        var table = $("#" + object.tableId);
        if (table != null) {
            $("#" + object.tableId + " tr").remove(".data")
        };
        $("#" + object.tableId + "_head").after(listHtml);
        if ($.isFunction(object.setTableHeadInfo)) {
            object.setTableHeadInfo(object.data)
        };
        if ($.isFunction(object.callbackFun)) {
            object.callbackFun(object.data)
        }
    };
    object.renderByPage = function (pageNo, cacheSize) {
        if (pageNo <= cacheSize) {
            object.render(object.dataCache, ((pageNo - 1) * object.pagination.pageSize + 1), ((pageNo - 1) * object.pagination.pageSize + (object.pagination.pageSize)))
        } else {
            var beginPage = object.pagination.getBeginPage(pageNo);
            var endPage = object.pagination.getEndPage(pageNo);
            var pageSize = object.pagination.pageSize;
            var begin = (pageNo - beginPage) * pageSize + 1;
            var end = (pageNo - beginPage) * pageSize + pageSize;
            object.render(object.dataCache, begin, end)
        }
    };
    object.mergeJson = function mergeJson(jsonbject1, jsonbject2) {
        var resultJsonObject = {};
        for (var attr in jsonbject1) {
            resultJsonObject[attr] = jsonbject1[attr]
        };
        for (var attr in jsonbject2) {
            resultJsonObject[attr] = jsonbject2[attr]
        };
        return resultJsonObject
    };
    object.setTableClass = function (className) {
        object.tableClassName = className
    };
    object.setCallbackFun = function (fun) {
        object.callbackFun = fun
    };
    return object
};
var Pagination = function (tableContainer) {
    var object = new Object;
    object.tableContainer = tableContainer;
    object.pageHelp = {};
    object.pageNo;
    object.pageCount;
    object.pageSize;
    object.cacheSize;
    object.paginationHtml;
    object.init = function () {
        object.pageNo = object.pageHelp.pageNo;
        object.pageCount = object.pageHelp.pageCount;
        object.pageSize = object.pageHelp.pageSize;
        object.cacheSize = object.pageHelp.cacheSize;
        object.composite();
        object.bindEvent();
    };
    object.composite = function () {
        var ifBindEvent = false;
        if ($('#' + object.tableContainer.tableId + '_pagination') != null || $('#' + object.tableContainer.tableId + '_pagination') != undefined) {
            $('#' + object.tableContainer.tableId + '_pagination').remove();
            ifBindEvent = true
        };
        var page = '<table id="' + object.tableContainer.tableId + '_pagination"  border="0" cellpadding="0" cellspacing="0" width="100%">';
        page += '<tr align="right">';
        if (object.pageNo < object.pageCount && object.pageNo > 1) {
            page += '<td  class="nextpage" bgcolor="#FFFFFF" height="0" valign="top" align="right">' + '第' + object.pageNo + '页/共' + object.pageCount + '页';
            page += '转到<input name="pageid" size="2" id="' + object.tableContainer.tableId + '_pageid" type="text" />页' + '</td><td width="26"><a href="#"><img  id="' + object.tableContainer.tableId + '_togo" src="/images/bt_go.gif" alt="" border="0" /></a>';
            page += '</td><td width="80"><a title="上页" href="#" id="' + object.tableContainer.tableId + '_before" pageNo="' + (object.pageNo - 1) + '"> 上一页</a>';
            page += '<a title="下页" href="#"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(object.pageNo) + 1) + '">下一页 </a> ';
            page += '</td>'
        };
        page += '</tr>';
        if (object.pageNo < object.pageCount && object.pageNo == 1) {
            page += '<td width="100%" class="nextpage" bgcolor="#FFFFFF" height="0" valign="top">第' + object.pageNo + '页/共' + object.pageCount + '页';
            page += '转到<input name="pageid" size="2" id="' + object.tableContainer.tableId + '_pageid" width="3" type="text">页' + '</td><td width="26"><a href="#"><img  id="' + object.tableContainer.tableId + '_togo" src="/images/bt_go.gif" alt="" border="0"></a>';
            page += '</td><td width="80"><a title="下页" href="#"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(object.pageNo) + 1) + '">下一页 </a>';
            page += '</td>'
        };
        if (object.pageNo >= object.pageCount && object.pageNo > 1) {
            page += '<td width="100%" class="nextpage" bgcolor="#FFFFFF" height="0" valign="top">第' + object.pageNo + '页/共' + object.pageCount + '页';
            page += '转到<input name="pageid" size="2" id="' + object.tableContainer.tableId + '_pageid" width="3" type="text">页' + '</td><td width="26"><a href="#">' + '<img class="right" id="' + object.tableContainer.tableId + '_togo" src="/images/bt_go.gif" alt="" border="0"></a>';
            page += '</td><td width="80"><a title="上页" href="#"  id="' + object.tableContainer.tableId + '_before" pageNo="' + (object.pageNo - 1) + '"> 上一页</a>';
            page += '</td>'
        };
        if (object.pageCount == 1 && object.pageNo == 1 || object.pageCount == 0) {
            page += ''
        };
        page += '</tr>';
        page += '</table>';
        $("#" + object.tableContainer.targetDivId).append(page);
        if (ifBindEvent) {
            object.bindEvent();
        }
    };
    object.bindEvent = function () {
        if (object.pageNo < object.pageCount && object.pageNo > 1) {
            $('#' + object.tableContainer.tableId + '_before').unbind('click');
            $('#' + object.tableContainer.tableId + '_next').unbind('click');
            $('#' + object.tableContainer.tableId + '_togo').unbind('click');
            $('#' + object.tableContainer.tableId + '_pageid').unbind('keydown');
            $('#' + object.tableContainer.tableId + '_before').bind('click', object.beforePage);
            $('#' + object.tableContainer.tableId + '_next').bind('click', object.nextPage);
            $('#' + object.tableContainer.tableId + '_togo').bind('click', object.forwordPage);
            $('#' + object.tableContainer.tableId + '_pageid').bind('keydown', object.forwordPageKeydown)
        };
        if (object.pageNo < object.pageCount && object.pageNo == 1) {
            $('#' + object.tableContainer.tableId + '_next').unbind('click');
            $('#' + object.tableContainer.tableId + '_togo').unbind('click');
            $('#' + object.tableContainer.tableId + '_pageid').unbind('keydown');
            $('#' + object.tableContainer.tableId + '_next').bind('click', object.nextPage);
            $('#' + object.tableContainer.tableId + '_togo').bind('click', object.forwordPage);
            $('#' + object.tableContainer.tableId + '_pageid').bind('keydown', object.forwordPageKeydown)
        };
        if (object.pageNo >= object.pageCount && object.pageNo > 1) {
            $('#' + object.tableContainer.tableId + '_before').unbind('click');
            $('#' + object.tableContainer.tableId + '_togo').unbind('click');
            $('#' + object.tableContainer.tableId + '_pageid').unbind('keydown');
            $('#' + object.tableContainer.tableId + '_before').bind('click', object.beforePage);
            $('#' + object.tableContainer.tableId + '_togo').bind('click', object.forwordPage);
            $('#' + object.tableContainer.tableId + '_pageid').bind('keydown', object.forwordPageKeydown)
        };
        $('.' + object.tableContainer.tableId + '_paginationNumLink').unbind('click');
        $('.' + object.tableContainer.tableId + '_paginationNumLink').bind('click', object.pageNumEvent)
    };
    object.beforePage = function (event) {
        var changedPageNo = $('#' + object.tableContainer.tableId + '_before').attr('pageNo');
        var pageSize = object.pageSize;
        var cacheSize = object.cacheSize;
        if (object.isOutOfCache(changedPageNo)) {
            object.pageNo = changedPageNo;
            object.tableContainer.renderByPage(changedPageNo, cacheSize);
            object.composite()
        } else {
            object.tableContainer.stoploadingImg();
            object.tableContainer.startloadingImg();
            $("#" + object.tableContainer.tableId + "_pagination").empty();
            $("#" + object.tableContainer.tableId + "_dataList").empty();
            var params = {
                'pageHelp.pageNo': changedPageNo
            };
            params = new mergeJson(object.tableContainer.params, params);
            params = new mergeJson(params, object.getPageRange(changedPageNo));
            if (object.tableContainer.sortable == true) {
                params = new mergeJson(params, object.tableContainer.sorter.params)
            };
            var ajaxSuccessCallback = function (data) {
                object.tableContainer.stoploadingImg();
                object.tableContainer.dataCache = data.result;
                object.tableContainer.data = data;
                object.pageNo = changedPageNo;
                object.tableContainer.renderByPage(changedPageNo, cacheSize);
                object.composite()
            };
            var ajaxParam = {
                type: "get",
                cache: false,
                url: object.tableContainer.AjaxUrl,
                data: params,
                dataType: "jsonp",
                async: true,
                jsonp: "jsonCallBack",
                jsonpCallback: "jsonpCallback" + Math.floor(Math.random() * (100000 + 1)),
                timeout: 30000,
                success: ajaxSuccessCallback,
                error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == "timeout") {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    } else {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    }
                }
            };
            $.ajax(ajaxParam)
        }
    };
    object.nextPage = function (event) {
        var changedPageNo = $('#' + object.tableContainer.tableId + '_next').attr('pageNo');
        var pageSize = object.pageSize;
        var cacheSize = object.cacheSize;
        if (object.isOutOfCache(changedPageNo)) {
            object.pageNo = changedPageNo;
            object.tableContainer.renderByPage(changedPageNo, cacheSize);
            object.composite()
        } else {
            object.tableContainer.stoploadingImg();
            object.tableContainer.startloadingImg();
            $("#" + object.tableContainer.tableId + "_pagination").empty();
            $("#" + object.tableContainer.tableId + "_dataList").empty();
            var params = {
                'pageHelp.pageNo': changedPageNo
            };
            params = new mergeJson(object.tableContainer.params, params);
            params = new mergeJson(params, object.getPageRange(changedPageNo));
            if (object.tableContainer.sortable == true) {
                params = new mergeJson(params, object.tableContainer.sorter.params)
            };
            var ajaxSuccessCallback = function (data) {
                object.tableContainer.stoploadingImg();
                object.tableContainer.dataCache = data.result;
                object.tableContainer.data = data;
                object.pageNo = changedPageNo;
                object.tableContainer.renderByPage(changedPageNo, cacheSize);
                object.composite()
            };
            var ajaxParam = {
                type: "get",
                url: object.tableContainer.AjaxUrl,
                data: params,
                dataType: "jsonp",
                async: true,
                cache: false,
                jsonp: "jsonCallBack",
                jsonpCallback: "jsonpCallback" + Math.floor(Math.random() * (100000 + 1)),
                timeout: 30000,
                success: ajaxSuccessCallback,
                error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == "timeout") {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    } else {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    }
                }
            };
            $.ajax(ajaxParam)
        }
    };
    object.forwordPage = function (event) {
        var changedPageNo = $('#' + object.tableContainer.tableId + '_pageid').attr('value');
        if (changedPageNo == undefined || changedPageNo == null || changedPageNo.length === 0) {
            if (language == "cn") alert("请输入页数.");
            else if (language == "en") alert("Please input page number.");
            return
        };
        if (isNaN(changedPageNo)) {
            if (language == "cn") alert("请输入正确的页数.");
            else if (language == "en") alert("Please input correct page number.");
            return
        };
        if (Number(changedPageNo) < 1) {
            changedPageNo = 1
        };
        changedPageNo = Number(changedPageNo);
        var pageNoDefine = Math.ceil(Number(changedPageNo));
        if (changedPageNo < pageNoDefine) {
            if (language == "cn") alert("请输入正确的页数.");
            else if (language == "en") alert("Please input correct page number.");
            return
        };
        if (changedPageNo > object.pageCount) {
            changedPageNo = object.pageCount
        };
        var pageSize = object.pageSize;
        var cacheSize = object.cacheSize;
        if (object.isOutOfCache(changedPageNo)) {
            object.pageNo = changedPageNo;
            object.tableContainer.renderByPage(changedPageNo, cacheSize);
            object.composite()
        } else {
            object.tableContainer.stoploadingImg();
            object.tableContainer.startloadingImg();
            $("#" + object.tableContainer.tableId + "_pagination").empty();
            $("#" + object.tableContainer.tableId + "_dataList").empty();
            var params = {
                'pageHelp.pageNo': changedPageNo
            };
            params = new mergeJson(object.tableContainer.params, params);
            params = new mergeJson(params, object.getPageRange(changedPageNo));
            if (object.tableContainer.sortable == true) {
                params = new mergeJson(params, object.tableContainer.sorter.params)
            };
            var ajaxSuccessCallback = function (data) {
                object.tableContainer.stoploadingImg();
                object.tableContainer.dataCache = data.result;
                object.tableContainer.data = data;
                object.pageNo = changedPageNo;
                object.tableContainer.renderByPage(changedPageNo, cacheSize);
                object.composite()
            };
            var ajaxParam = {
                type: "get",
                url: object.tableContainer.AjaxUrl,
                data: params,
                dataType: "jsonp",
                async: true,
                cache: false,
                jsonp: "jsonCallBack",
                jsonpCallback: "jsonpCallback" + Math.floor(Math.random() * (100000 + 1)),
                timeout: 30000,
                success: ajaxSuccessCallback,
                error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == "timeout") {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    } else {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    }
                }
            };
            $.ajax(ajaxParam)
        }
    };
    object.forwordPageKeydown = function (event) {
        if (event.keyCode == 13) {
            object.forwordPage(event)
        } else {
            return
        }
    };
    object.pageNumEvent = function (event) {
        var pageNo = $(this).attr('pageNo');
        var changedPageNo = parseInt(pageNo);
        var pageSize = object.pageSize;
        var cacheSize = object.cacheSize;
        if (object.isOutOfCache(changedPageNo)) {
            object.pageNo = changedPageNo;
            object.tableContainer.renderByPage(changedPageNo, cacheSize);
            object.composite()
        } else {
            object.tableContainer.stoploadingImg();
            object.tableContainer.startloadingImg();
            $("#" + object.tableContainer.tableId + "_pagination").empty();
            $("#" + object.tableContainer.tableId + "_dataList").empty();
            var params = {
                'pageHelp.pageNo': changedPageNo
            };
            params = new mergeJson(object.tableContainer.params, params);
            params = new mergeJson(params, object.getPageRange(changedPageNo));
            if (object.tableContainer.sortable == true) {
                params = new mergeJson(params, object.tableContainer.sorter.params)
            };
            var ajaxSuccessCallback = function (data) {
                object.tableContainer.stoploadingImg();
                object.tableContainer.dataCache = data.result;
                object.tableContainer.data = data;
                object.pageNo = changedPageNo;
                object.tableContainer.renderByPage(changedPageNo, cacheSize);
                object.composite()
            };
            var ajaxParam = {
                type: "get",
                cache: false,
                url: object.tableContainer.AjaxUrl,
                data: params,
                dataType: "jsonp",
                async: true,
                jsonp: "jsonCallBack",
                jsonpCallback: "jsonpCallback" + Math.floor(Math.random() * (100000 + 1)),
                timeout: 30000,
                success: ajaxSuccessCallback,
                error: function (jqXHR, textStatus, errorThrown) {
                    if (textStatus == "timeout") {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    } else {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    }
                }
            };
            $.ajax(ajaxParam)
        }
    };
    object.getPageRange = function (changedPageNo) {
        var closeValue = Math.ceil(changedPageNo / object.cacheSize);
        var beginPage = (closeValue * object.cacheSize + 1 - object.cacheSize);
        var endPage = (closeValue * object.cacheSize);
        return {
            'pageHelp.beginPage': beginPage,
            'pageHelp.endPage': endPage
        }
    };
    object.getBeginPage = function (changedPageNo) {
        var closeValue = Math.ceil(changedPageNo / object.cacheSize);
        var beginPage = (closeValue * object.cacheSize + 1 - object.cacheSize);
        return beginPage
    };
    object.getEndPage = function (changedPageNo) {
        var closeValue = Math.ceil(changedPageNo / object.cacheSize);
        var endPage = (closeValue * object.cacheSize);
        return endPage
    };
    object.isOutOfCache = function (changedPageNo) {
        var num1 = Math.ceil(changedPageNo / object.cacheSize);
        var num2 = Math.ceil(object.pageNo / object.cacheSize);
        return num1 == num2
    };
    return object
};

function mergeJson(jsonbject1, jsonbject2) {
    var resultJsonObject = {};
    for (var attr in jsonbject1) {
        resultJsonObject[attr] = jsonbject1[attr]
    };
    for (var attr in jsonbject2) {
        resultJsonObject[attr] = jsonbject2[attr]
    };
    return resultJsonObject
};
var newPagination = function (tableContainer) {
    var object = new Pagination(tableContainer);
    object.paginationNum = 6;
    object.composite = function () {
        var ifBindEvent = false;
        if ($('#' + object.tableContainer.tableId + '_pagination') != null || $('#' + object.tableContainer.tableId + '_pagination') != undefined) {
            $('#' + object.tableContainer.tableId + '_pagination').remove();
            ifBindEvent = true
        };
        var currentPage = object.pageNo;
        var totalPage = object.pageCount;
        var page = "";
        if (object.pageNo < object.pageCount && object.pageNo > 1) {
            page = '<div id="' + object.tableContainer.tableId + '_pagination" class="paging">';
            page += '<div class="paging-bottom">';
            page += '<span class="paging_input"> ' + (language == 'cn' ? '共' : 'Total: ') + totalPage + (language == 'cn' ? '页 转到第' : 'Page Skip to') + '<input id="' + object.tableContainer.tableId + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>' + (language == 'cn' ? '页' : 'Page') + '<img src="/images/button_go.gif"  id="' + object.tableContainer.tableId + '_togo" title="指定页码"></a></span>';
            page += '<span class="paging_text">';
            page += '<a class="paging_pre" title="上页" href="javascript:void(0)" id="' + object.tableContainer.tableId + '_before" pageNo="' + (currentPage - 1) + '"> <span> ' + (language == 'cn' ? '上一页' : 'Pre') + '</span> </a>';
            page += object.getButtonsPagination(currentPage);
            page += '<a class="paging_next" title="下页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(currentPage) + 1) + '"> <span>' + (language == 'cn' ? '下一页' : 'Next') + ' </span> </a>';
            page += '</span>';
            page += '</div>';
            page += '</div>'
        };
        if (object.pageNo < object.pageCount && object.pageNo == 1) {
            page = '<div id="' + object.tableContainer.tableId + '_pagination" class="paging">';
            page += '<div class="paging-bottom">';
            page += '<span class="paging_input"> ' + (language == 'cn' ? '共' : 'Total: ') + totalPage + (language == 'cn' ? '页 转到第' : 'Page Skip to') + '<input id="' + object.tableContainer.tableId + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>' + (language == 'cn' ? '页' : 'Page') + '<img src="/images/button_go.gif"  id="' + object.tableContainer.tableId + '_togo" title="指定页码"></a></span>';
            page += '<span class="paging_text">';
            page += object.getButtonsPagination(currentPage);
            page += '<a class="paging_next" title="下页" href="javascript:void(0)"  id="' + object.tableContainer.tableId + '_next" pageNo="' + (parseInt(currentPage) + 1) + '"> <span>' + (language == 'cn' ? '下一页' : 'Next') + ' </span> </a>';
            page += '</span>';
            page += '</div>';
            page += '</div>'
        };
        if (object.pageNo >= object.pageCount && object.pageNo > 1) {
            page = '<div id="' + object.tableContainer.tableId + '_pagination" class="paging">';
            page += '<div class="paging-bottom">';
            page += '<span class="paging_input"> ' + (language == 'cn' ? '共' : 'Total: ') + totalPage + (language == 'cn' ? '页 转到第' : 'Page Skip to') + '<input id="' + object.tableContainer.tableId + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>' + (language == 'cn' ? '页' : 'Page') + ' <a><img src="/images/button_go.gif"  id="' + object.tableContainer.tableId + '_togo" title="指定页码"></a></span>';
            page += '<span class="paging_text">';
            page += '<a class="paging_pre" title="上页" href="javascript:void(0)" id="' + object.tableContainer.tableId + '_before" pageNo="' + (currentPage - 1) + '"> <span> ' + (language == 'cn' ? '上一页' : 'Pre') + '</span> </a>';
            page += object.getButtonsPagination(currentPage);
            page += '</span>';
            page += '</div>';
            page += '</div>'
        };
        if (object.pageCount == 1 && object.pageNo == 1 || object.pageCount == 0) {
            page += ''
        };
        $("#" + object.tableContainer.targetDivId).append(page);
        if (ifBindEvent) {
            object.bindEvent();
        }
    };
    object.getButtonsPagination = function (pageNo) {
        var closeValue = Math.ceil(pageNo / object.paginationNum);
        var closeV = Math.ceil(object.paginationNum / 2);
        var beginPage = (closeValue * object.paginationNum + 1 - object.paginationNum);
        var endPage = (closeValue * object.paginationNum);
        if (pageNo > closeV) {
            var beginPage = (pageNo - closeV);
            var endPage = (parseInt(pageNo) + parseInt(object.paginationNum - closeV) - 1)
        };
        var html = '';
        if (endPage >= parseInt(object.pageCount)) {
            endPage = object.pageCount
        };
        for (var i = beginPage; i <= endPage; i++) {
            if (parseInt(pageNo) === i) {
                html += '<span class="paging_num_on">' + i + '</span>'
            } else {
                html += '<a class="' + object.tableContainer.tableId + '_paginationNumLink" href="#" pageNo=' + i + '>' + i + '</a>'
            }
        };
        if (endPage < object.pageCount) {
            html += '<span class="page-break">...</span>'
        };
        return html
    };
    return object
};
var noticeContainer = function (isPagination, targetDivId, tableId) {
    var object = new Container(isPagination, targetDivId, tableId);
    object.queryDate = "";
    object.init = function (headers) {
        object.headAarry = headers;
        $("#" + object.targetDivId).append(object.headerHtml)
    };
    object.render = function (result, begin, end) {
        var listHtml = "";
        listHtml += '<div id="' + object.tableId + '_dataList"><span class="date">' + ($.isEmptyObject(object.queryDate) ? '' : object.queryDate) + '</span>';
        listHtml += '<ul class="list_ul">';
        $("#" + object.targetDivId).find("#" + object.tableId + "_dataList").remove();
        if (end > result.length) {
            end = result.length
        };
        var p = object.pagination;
        var b = (p.pageNo - 1) * p.pageSize;
        var t = 0;
        for (var i = begin; i <= end; i++) {
            t++;
            var data = result[(i - 1)];
            for (var a = 0; a < object.headAarry.length; a++) {
                var headPros = object.headAarry[a];
                var hidden = headPros.hidden;
                var formatter = headPros.formatter;
                var withRecordNum = headPros.withRecordNum;
                if (hidden != undefined && hidden) {
                    listHtml += '<li style="display:none">'
                } else {
                    listHtml += '<li>'
                }; if (withRecordNum) {
                    var num = String(Number(b) + Number(t));
                    if (num.length == 1) {
                        num = "0" + num
                    };
                    listHtml += '<span>' + num + '</span>  '
                };
                if ((typeof formatter) == 'function') {
                    listHtml += formatter(data)
                } else {
                    listHtml += eval('data.' + headPros.code)
                };
                listHtml += '</li>'
            }
        };
        listHtml += '</ul></div>';
        var table = $("#" + object.tableId);
        if (table != null) {
            $("#" + object.tableId).find("#" + object.tableId + "_dataList").remove()
        };
        $("#" + object.tableId).append(listHtml);
        if ($.isFunction(object.setTableHeadInfo)) {
            object.setTableHeadInfo(object.data)
        };
        if ($.isFunction(object.callbackFun)) {
            object.callbackFun(object.data)
        }
    };
    object.getDataByAjax = function (url, params, callbackFun) {
        object.AjaxUrl = url;
        object.params = utils.mergeJson(object.params, params);
        var p = object.params;
        object.callbackFun = callbackFun;
        object.startloadingImg();
        var ajaxSuccessCallback = function (data) {
            object.stoploadingImg();
            if (!$.trim(data.success).length == 0) {
                if ($.trim(data.success) == "false") {
                    if ($.trim(data.errorType).length != 0) {
                        if ($.trim(data.errorType) == "IPtableException" || $.trim(data.errorType) == "IpCountException") {
                            if (language == "cn") window.location.href = "/ip.htm";
                            else if (language == "en") window.location.href = "/ip_en.htm"
                        } else if ($.trim(data.errorType) == "IllegalParametersException") {
                            if (language == "cn") alert("您输入的查询条件中含有非法字符.");
                            if (language == "en") alert("The Conditions You Input Contains Illegal Parameters.")
                        };
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    } else {
                        if (language == "cn") window.location.href = "/500.htm";
                        else if (language == "en") window.location.href = "/500_en.htm"
                    };
                    return
                }
            };
            object.dataCache = data.result;
            object.queryDate = data.queryDate;
            object.data = data;
            var pagination = new newPagination(object);
            if (object.isPagination == true) {
                object.pagination = pagination;
                pagination.pageHelp = data.pageHelp;
                pagination.init()
            };
            if (object.isPagination == true) {
                object.render(object.dataCache, ((Math.ceil(pagination.pageNo / pagination.cacheSize) - 1) * pagination.pageSize + 1), ((Math.ceil(pagination.pageNo / pagination.cacheSize) - 1) * pagination.pageSize + pagination.pageSize))
            } else {
                object.render(object.dataCache, 1, object.dataCache.length)
            }
        };
        var ajaxParam = {
            type: "get",
            url: url,
            data: p,
            dataType: "jsonp",
            async: true,
            cache: false,
            jsonp: "jsonCallBack",
            jsonpCallback: "jsonpCallback" + Math.floor(Math.random() * (100000 + 1)),
            timeout: 30000,
            success: ajaxSuccessCallback,
            error: function (jqXHR, textStatus, errorThrown) {
                if (textStatus == "timeout") {
                    if (language == "cn") window.location.href = "/500.htm";
                    else if (language == "en") window.location.href = "/500_en.htm"
                } else {
                    if (language == "cn") window.location.href = "/500.htm";
                    else if (language == "en") window.location.href = "/500_en.htm"
                }
            }
        };
        try {
            $.ajax(ajaxParam)
        } catch (e) {
            if (language == "cn") window.location.href = "/500.htm";
            else if (language == "en") window.location.href = "/500_en.htm"
        }
    };
    return object
};
var sorter = function (container) {
    var object = new Object;
    object.defaultDirection = "asc";
    object.targetContainer = container;
    object.params = {};
    object.getImgHtml = function (headInfo) {
        var direction = headInfo.direction;
        if (direction == null || direction == undefined) {
            direction = object.defaultDirection
        };
        var imgHtml = "";
        if (direction == 'desc') {
            imgHtml = '<img id="' + object.targetContainer.tableId + '_arrow" src="/images/xia.gif">'
        } else if (direction == 'asc') {
            imgHtml = '<img id="' + object.targetContainer.tableId + '_arrow"  src="/images/shang.gif">'
        };
        return imgHtml
    };
    object.addEvent = function () {
        $('#' + object.targetContainer.tableId + ' th').each(function (i) {
            var sortable = $(this).attr('sortable');
            if (sortable == "true") {
                $(this).bind('click', function () {
                    var description = $(this).attr('desc');
                    var direction = $(this).attr('direction');
                    var index = $(this).attr('index');
                    var code = $(this).attr('id');
                    var istarget = $(this).attr('istarget');
                    if (!Boolean(istarget) || istarget === 'false') {
                        direction = 'asc'
                    } else {
                        if (direction === 'asc') {
                            direction = 'desc'
                        } else if (direction === 'desc') {
                            direction = 'asc'
                        } else {
                            direction = 'asc'
                        }
                    };
                    $(this).attr('istarget', true);
                    $(this).empty();
                    if (direction === 'desc') {
                        $(this).attr('direction', 'desc');
                        $(this).append('<a href="#">' + description + '<img id="' + object.targetContainer.tableId + '_arrow" src="/images/xia.gif"></a>')
                    } else if (direction === 'asc') {
                        $(this).attr('direction', 'asc');
                        $(this).append('<a href="#">' + description + '<img id="' + object.targetContainer.tableId + '_arrow" src="/images/shang.gif"></a>')
                    } else {
                        $(this).attr('direction', 'asc');
                        $(this).append('<a href="#">' + description + '<img id="' + object.targetContainer.tableId + '_arrow" src="/images/shang.gif"></a>')
                    };
                    $('#' + object.targetContainer.tableId + ' th').each(function (p) {
                        var index2 = $(this).attr('index');
                        var sortable2 = $(this).attr('sortable');
                        var description2 = $(this).attr('desc');
                        if (sortable2 === 'undefined' || sortable2 === null) {
                            sortable2 = false
                        };
                        if (index2 != index && sortable2) {
                            $(this).empty();
                            $(this).append('<a href="#">' + description2 + '</a>');
                            $(this).attr('istarget', false)
                        }
                    });
                    direction = $(this).attr('direction');
                    object.params = {
                        'sortName': code,
                        'direction': direction
                    };
                    object.targetContainer.getDataByAjax(object.targetContainer.AjaxUrl, object.params, null)
                })
            }
        })
    };
    return object
};
var staticPagination = function (p) {
    var object = new Object();
    object.pageNo = p.pageNo;
    object.pageCount = p.pageCount;
    object.globalName = p.btnName;
    object.targetContainer = p.target;
    object.paginationNum = 6;
    object.total = p.total;
    object.cacheSize = 5;
    object.composite = function () {
        var ifBindEvent = false;
        if ($('#' + object.globalName + '_pagination') != null || $('#' + object.globalName + '_pagination') != undefined) {
            $('#' + object.globalName + '_pagination').remove();
            ifBindEvent = true
        };
        var currentPage = object.pageNo;
        var totalPage = object.pageCount;
        var page = "";
        if (object.pageNo < object.pageCount && object.pageNo > 1) {
            page = '<div id="' + object.globalName + '_pagination" class="paging">';
            page += '<div class="paging-bottom">';
            page += '<span class="paging_input"> ' + (language == 'cn' ? '共' : 'Total: ') + totalPage + (language == 'cn' ? '页 转到第' : 'Page Skip to') + '<input id="' + object.globalName + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>' + (language == 'cn' ? '页' : 'Page') + ' <a><img src="/images/button_go.gif"  id="' + object.globalName + '_togo" title="指定页码" type="submit"></a></span>';
            page += '<span class="paging_text">';
            page += '<a class="paging_pre" title="上页" href="#" id="' + object.globalName + '_before" pageNo="' + (currentPage - 1) + '"> <span> ' + (language == 'cn' ? '上一页' : 'Pre') + '</span> </a>';
            page += object.getButtonsPagination(currentPage);
            page += '<a class="paging_next" title="下页" href="#"  id="' + object.globalName + '_next" pageNo="' + (parseInt(currentPage) + 1) + '">' + (language == 'cn' ? '下一页' : 'Next') + ' </a>';
            page += '</span>';
            page += '</div>';
            page += '</div>'
        };
        if (object.pageNo < object.pageCount && object.pageNo == 1) {
            page = '<div id="' + object.globalName + '_pagination" class="paging">';
            page += '<div class="paging-bottom">';
            page += '<span class="paging_input"> ' + (language == 'cn' ? '共' : 'Total: ') + totalPage + (language == 'cn' ? '页 转到第' : 'Page Skip to') + '<input id="' + object.globalName + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>' + (language == 'cn' ? '页' : 'Page') + ' <a><img src="/images/button_go.gif"  id="' + object.globalName + '_togo" title="指定页码" type="submit"></a></span>';
            page += '<span class="paging_text">';
            page += object.getButtonsPagination(currentPage);
            page += '<a class="paging_next" title="下页" href="#"  id="' + object.globalName + '_next" pageNo="' + (parseInt(currentPage) + 1) + '"> ' + (language == 'cn' ? '下一页' : 'Next') + '  </a>';
            page += '</span>';
            page += '</div>';
            page += '</div>'
        };
        if (object.pageNo >= object.pageCount && object.pageNo > 1) {
            page = '<div id="' + object.globalName + '_pagination" class="paging">';
            page += '<div class="paging-bottom">';
            page += '<span class="paging_input"> ' + (language == 'cn' ? '共' : 'Total: ') + totalPage + (language == 'cn' ? '页 转到第' : 'Page Skip to') + '<input id="' + object.globalName + '_pageid" type="text" title="指定页码" name="jumpto" size="3" value=""/>' + (language == 'cn' ? '页' : 'Page') + '<a><img src="/images/button_go.gif"  id="' + object.globalName + '_togo" title="指定页码" type="submit"></a></span>';
            page += '<span class="paging_text">';
            page += '<a class="paging_pre" title="上页" href="#" id="' + object.globalName + '_before" pageNo="' + (currentPage - 1) + '">  ' + (language == 'cn' ? '上一页' : 'Pre') + ' </a>';
            page += object.getButtonsPagination(currentPage);
            page += '</span>';
            page += '</div>';
            page += '</div>'
        };
        if (object.pageCount == 1 && object.pageNo == 1 || object.pageCount == 0) {
            page += ''
        };
        $("#" + object.targetContainer).append(page);
        if (ifBindEvent) {
            object.bindEvent();
        }
    };
    object.getButtonsPagination = function (pageNo) {
        var closeValue = Math.ceil(pageNo / object.paginationNum);
        var closeV = Math.ceil(object.paginationNum / 2);
        var beginPage = (closeValue * object.paginationNum + 1 - object.paginationNum);
        var endPage = (closeValue * object.paginationNum);
        if (pageNo > closeV) {
            var beginPage = (pageNo - closeV);
            var endPage = (parseInt(pageNo) + parseInt(object.paginationNum - closeV) - 1)
        };
        var html = '';
        if (endPage >= parseInt(object.pageCount)) {
            endPage = object.pageCount
        };
        for (var i = beginPage; i <= endPage; i++) {
            if (parseInt(pageNo) === i) {
                html += '<span class="paging_num_on">' + i + '</span>'
            } else {
                html += '<a class="' + object.globalName + '_paginationNumLink" href="javascript:void(0)" pageNo=' + i + '>' + i + '</a>'
            }
        };
        if (endPage < object.pageCount) {
            html += '<span class="page-break">...</span>'
        };
        return html
    };
    object.bindEvent = function () {
        if (object.pageNo < object.pageCount && object.pageNo > 1) {
            $('#' + object.globalName + '_before').unbind('click');
            $('#' + object.globalName + '_next').unbind('click');
            $('#' + object.globalName + '_togo').unbind('click');
            $('#' + object.globalName + '_pageid').unbind('keydown');
            $('#' + object.globalName + '_before').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_next').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_togo').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_pageid').bind('keydown', object.forwordPageKeydown)
        };
        if (object.pageNo < object.pageCount && object.pageNo == 1) {
            $('#' + object.globalName + '_next').unbind('click');
            $('#' + object.globalName + '_togo').unbind('click');
            $('#' + object.globalName + '_pageid').unbind('keydown');
            $('#' + object.globalName + '_next').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_togo').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_pageid').bind('keydown', object.forwordPageKeydown)
        };
        if (object.pageNo >= object.pageCount && object.pageNo > 1) {
            $('#' + object.globalName + '_before').unbind('click');
            $('#' + object.globalName + '_togo').unbind('click');
            $('#' + object.globalName + '_pageid').unbind('keydown');
            $('#' + object.globalName + '_before').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_togo').bind('click', object.dynamicRequest);
            $('#' + object.globalName + '_pageid').bind('keydown', object.forwordPageKeydown)
        };
        $('.' + object.globalName + '_paginationNumLink').unbind('click');
        $('.' + object.globalName + '_paginationNumLink').bind('click', object.dynamicRequest)
    };
    object.dynamicRequest = function (event) {
        var pageNo = $(this).attr("pageno");
        if (pageNo == undefined || pageNo == null) {
            pageNo = $("#" + object.globalName + "_pageid").val()
        };
        if (pageNo == undefined || pageNo == null || pageNo.length === 0) {
            if (language == "cn") alert("请输入正确的页数");
            else if (language == "en") alert("Please input page number.");
            return
        };
        if (isNaN(pageNo)) {
            if (language == "cn") alert("请输入正确的页数");
            else if (language == "en") alert("Please input correct page number.");
            return
        };
        if (Number(pageNo) < 1) {
            pageNo = 1
        };
        pageNo = Number(pageNo);
        var pageNoDefine = Math.ceil(Number(pageNo));
        if (pageNo < pageNoDefine) {
            if (language == "cn") alert("请输入正确的页数");
            else if (language == "en") alert("Please input correct page number.");
            return
        };
        if (pageNo > object.pageCount) {
            pageNo = object.pageCount
        };
        var ps = {
            'pageHelp.pageNo': pageNo,
            'pageHelp.beginPage': object.getBeginPage(pageNo),
            'pageHelp.endPage': object.getEndPage(pageNo)
        };
        var method = p.method;
        method(ps)
    };
    object.forwordPageKeydown = function (event) {
        if (event.keyCode == 13) {
            object.dynamicRequest(event)
        } else {
            return
        }
    };
    object.getBeginPage = function (changedPageNo) {
        var closeValue = Math.ceil(changedPageNo / object.cacheSize);
        var beginPage = (closeValue * object.cacheSize + 1 - object.cacheSize);
        return beginPage
    };
    object.getEndPage = function (changedPageNo) {
        var closeValue = Math.ceil(changedPageNo / object.cacheSize);
        var endPage = (closeValue * object.cacheSize);
        return endPage
    };
    return object
};