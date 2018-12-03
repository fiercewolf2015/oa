 <%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                   <div class="dropdown profile-element"> 
                   		<span>
                            <img alt="image" class="img-circle" src="${ctx }/getPhoto" />
                        </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear">
                            	<span class="block m-t-xs">
                            		<strong class="font-bold">
                            			<shiro:principal property="name"/>
                            			<b class="caret"></b>
                           			</strong>
                             	</span>
                           	</span> 
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="${ctx }/profile">个人设置</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx }/logout">注销</a></li>
                        </ul>
                    </div>
                </li>
                <li class="active firstLevel">
                    <a href="#"><span class="nav-label">-企业管理</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse">
                    	<shiro:hasPermission name="dept">
	                        <li class="active">
	                            <a href="#">-组织管理<span class="fa arrow"></span></a>
	                            <ul class="nav nav-third-level">
	                        			  <li id="departmentLi"><a href="${ctx }/department">-部门管理</a></li>
	                        			  <li id="occupationLi"><a href="${ctx }/occupation">-职务管理</a></li>
	                                 	  <li id="postLi"><a href="${ctx }/post">-岗位管理</a></li>
	                        			  <li id="titleLi" class ="active"><a href="${ctx }/title">-职级职称管理</a></li>
	                            </ul>
	                        </li>
	                    </shiro:hasPermission>
                        <shiro:hasPermission name="staff">
	                        <li>
	                            <a href="#">-人员管理<span class="fa arrow"></span></a>
	                            <ul class="nav nav-third-level">
	                        			  <li id="staffLi"><a href="${ctx }/staff">-员工管理</a></li>
	                        			  <li ><a href="#">-人员项目管理</a></li>
	                        			  <li ><a href="#">-离职记录查询</a></li>
	                        			  <li ><a href="#">-转正记录查询</a></li>
	                        			  <li ><a href="#">-复职记录查询</a></li>
	                        			  <li ><a href="#">-员工异动管理</a></li>
	                        			  <li ><a href="#">-异动记录查询</a></li>
	                        			  <li ><a href="#">-年假参数设置</a></li>
	                        			  <li ><a href="#">-年假规则设置</a></li>
	                        			  <li ><a href="#">-员工年假查询</a></li>
	                            </ul>
	                        </li>
                        </shiro:hasPermission>
                    </ul>
                </li>
                <shiro:hasPermission name="workflow">
	                <li class="firstLevel">
	                    <a href="#"><span class="nav-label">-流程管理</span><span class="fa arrow"></span></a>
	                    <ul class="nav nav-second-level collapse">
	              			 <%--  <li >
	              			  	   <a href="#">-发起流程<span class="fa arrow"></span></a>
	                            <ul class="nav nav-third-level">
											<li id="overtimeFormLi"  class ="active"><a href="${ctx }/workflow/progress/48/overtimeForm">-加班表单</a></li>
											<li id="leaveFormLi"><a href="${ctx }/leaveForm">-请假表单</a></li>
											<li id="fileAccessFormLi"><a href="${ctx }/fileAccessForm">-文件阅办表单</a></li>
											<li id="financialBillsFormLi"><a href="${ctx }/financialBillsForm">-财务票据审批单</a></li>
											<li id="partyFileFormLi"><a href="${ctx }/partyFileForm">-党委文件阅办</a></li>
											<li id="useSealFormLi"><a href="${ctx }/useSealForm">-用印审批</a></li>
											<li id="fixedAssetsLi"><a href="${ctx }/fixedAssets">-固定资产审批</a></li>
											<li id="contractWillFormLi"><a href="${ctx }/contractWillForm">-合同会审批</a></li>
											<li id="goOutFormLi"><a href="${ctx }/goOutForm">-外出审批单</a></li>
											<li id="salaryFormLi"><a href="${ctx }/salaryForm">-工资审批单</a></li>
											<li id="addOrRemoveStaffFormLi"><a href="${ctx }/addOrRemoveStaffForm">-增减人员申报审批单</a></li>
											<li id="expandRewardLi"><a href="${ctx }/expandReward">-市场拓展奖励审批流程</a></li>
											<li id="supplierFormLi"><a href="${ctx }/supplierForm">-供应商审批单</a></li>
											<li id="companyVIFormLi"><a href="${ctx }/companyVIForm">-公司VI审批单</a></li>
											<li id="postChangeFormLi"><a href="${ctx }/postChangeForm">-公司岗位异动审批单</a></li>
											<li id="spareFormLi"><a href="${ctx }/spareForm">-备用表单</a></li>
	                            </ul>
	              			  </li> --%>
	              			  <li id="instanceTodolistLi"><a href="${ctx }/workflowInstance/todolist">-待办流程</a></li>
	              			  <li id="instanceDonelistLi"><a href="${ctx }/workflowInstance/donelist">-已办流程</a></li>
	              			  <li id="instanceHistorylistLi"><a href="${ctx }/workflowInstance/historylist">-历史流程</a></li>
	              			  <shiro:hasPermission name="allProcess">
	              			  	<li id="allProcesslistLi"><a href="${ctx }/workflowInstance/allProcess">-所有流程</a></li>
	              			  </shiro:hasPermission>
	                    </ul>
	                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="budgeting">
	                 <li class="firstLevel">
	                    <a href="#"><span class="nav-label">-预算管理</span><span class="fa arrow"></span></a>
	                    <ul class="nav nav-second-level collapse">
	              			  <li id="budgetingLi"><a href="${ctx}/budgeting">-预算编制</a></li>
	              			  <li id="budgetApprovalLi"><a href="${ctx}/budgetApproval">-预算审批</a></li>
	                    </ul>
	                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="meeting">
	                <li class="firstLevel">
	                    <a href="#"><span class="nav-label">-会议管理</span><span class="fa arrow"></span></a>
	                    <ul class="nav nav-second-level collapse">
	              			  <li id="meetingLi"><a href="${ctx}/meeting">-会议申请</a></li>
	              			  <li id="meetingRemindLi"><a href="${ctx}/meeting/remind">-会议提醒</a></li>
	              			  <li id="meetingHistoryLi"><a href="${ctx}/meeting/history">-会议历史</a></li>
	                    </ul>
	                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="statistics">
	                <li class="firstLevel">
	                    <a href="#"><span class="nav-label">-统计分析</span><span class="fa arrow"></span></a>
	                    <ul class="nav nav-second-level collapse">
	              			  <li id="contractWillFormLi"><a href="${ctx}/contractWillStatistic">-合同经营数据分析</a></li>
	                    </ul>
	                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="system">
	                <li class="firstLevel">
	                    <a href="#"><span class="nav-label">-系统设置</span><span class="fa arrow"></span></a>
	                    <ul class="nav nav-second-level collapse">
	                    	<shiro:hasPermission name="role">
	              			  	  <li id="roleLi"><a href="${ctx}/role">-角色权限设置</a></li>
	              			 </shiro:hasPermission>
	              			 <shiro:hasPermission name="user">
		              			  <li id="userLi"><a href="${ctx}/user">-用户管理</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="overtime">
		              			  <li id="overtiemTypeLi"><a href="${ctx}/overtimeType">-加班类型设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="leave">
		              			  <li id="leaveTypeLi"><a href="${ctx}/leaveType">-请假类型设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="seal">
		              			  <li id="sealTypeLi"><a href="${ctx}/sealType">-印章类型设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="budgetCommissioner">
		              			  <li id="budgetCommissionerLi"><a href="${ctx}/budgetCommissioner">-预算专员设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="asset">
		              			  <li id="assetTypeLi"><a href="${ctx}/assetType">-资产业务类型设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="financeSubject">
		              			  <li id="financeSubjectLi"><a href="${ctx}/financeSubject">-会计科目设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="budgetMode">
		              			  <li id="budgetModelLi"><a href="${ctx}/budgetModel">-定义预算编制模板</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="financeLine">
		              			  <li id="financeLineLi"><a href="${ctx}/financeLine">-超预算百分比设置</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="customWorkflow">
		              			  <li id="workflowLi"><a href="${ctx}/workflow">-自定义流程</a></li>
		              		</shiro:hasPermission>
	              			  <shiro:hasPermission name="notice">
	              			  		<li id="noticeLi"><a href="${ctx}/notice">-通知管理</a></li>
	              			  </shiro:hasPermission>
	              			  <shiro:hasPermission name="contractHistory">
		              			  <li id="contractHistoryLi"><a href="${ctx}/contractWillHistory">-历史合同管理</a></li>
		              		 </shiro:hasPermission>
		              		 <shiro:hasPermission name="backUp">
		              			  <li id="backUpLi"><a href="${ctx}/backup">-数据备份</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="bizLog">
		              			  <li id="bizLogLi"><a href="${ctx}/bizActionLog">-日志管理</a></li>
		              		</shiro:hasPermission>
		              		<shiro:hasPermission name="become">
		              			  <li ><a href="#">-转正设置</a></li>
		              		</shiro:hasPermission>
	                    </ul>
	                </li>
               </shiro:hasPermission>
            </ul>
        </div>
    </nav>
</div>