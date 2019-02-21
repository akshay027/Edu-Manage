package com.maxlore.edumanage.API;

import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAbsentStudentResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.AssignResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject.AssignSubjectResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.EmployeeAttendanceResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance.StudentAttendanceResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminBranchesResponse.AdminBranches;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender.AdminCalenderResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminDashBoard.AdminDashboardResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminHolidays.HolidayResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave.EditLeaveResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminResignationResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTcResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTeacherAbsentResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.ClassTimeTableResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable.TeacherTimeTableResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.ClassTimeTableDetailResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails.TeacherTimeTableDetailResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride.BusLocationResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTrackride.TrackResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.StudentsResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail.TeachersResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.CalenderDateResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.ClassTeacherResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel.Teacherlist;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.DepartmentHeadResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.NoticeBoardResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.AdminPaymentHistoryModel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.DefaulterResponseModel;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels.FiltersResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.SubstituteTeacher.AbsentResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteSubjectResponse;
import com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo.SubstituteTeacherResponse;
import com.maxlore.edumanage.Models.ParentModels.Attnew.NewAttendanceResponseParent;
import com.maxlore.edumanage.Models.ParentModels.FeeHistory.FeeHistoryResponse;
import com.maxlore.edumanage.Models.ParentModels.FeeHistory.PaymentHistoryResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails.ParentAssignmentResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentDashboardResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.AcademicBusResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.AcademicFeeResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.BusFeeResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.FineDataResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.ParentFeeResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentFees.PayHistoryResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentLeaves.ParentLeaveResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentProfile.ParentProfileResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentSyllabus.ParentSyllabusResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile.ParentResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentTimeTable.ParentTimeTableResponse;
import com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate.ParentTansfercertiResponse;
import com.maxlore.edumanage.Models.ParentModels.SchoolFeeTypeResponse;
import com.maxlore.edumanage.Models.ParentModels.attendance.ParentAttendance;
import com.maxlore.edumanage.Models.StatusResponseClass;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailySessionResponse;
import com.maxlore.edumanage.Models.TeacherModels.Academics.DailyTopicResponse;
import com.maxlore.edumanage.Models.TeacherModels.Academics.MonthSessionResponse;
import com.maxlore.edumanage.Models.TeacherModels.Academics.YearSessionResponse;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentDetail.StudentStructure;
import com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigationbar.AssignmentStructure;
import com.maxlore.edumanage.Models.TeacherModels.AttendanceResponse;
import com.maxlore.edumanage.Models.TeacherModels.DashboardResponse;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.LeaveResponse;
import com.maxlore.edumanage.Models.TeacherModels.Leaves.StudentLeaveResponse;
import com.maxlore.edumanage.Models.TeacherModels.Newteachingplan.NewTeachingResponse;
import com.maxlore.edumanage.Models.TeacherModels.OwnAtt.OwnAttResponse;
import com.maxlore.edumanage.Models.TeacherModels.Profile.ProfileResponse;
import com.maxlore.edumanage.Models.TeacherModels.SignInResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentInfoResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentDetails.StudentResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentInfor.StudentInformationResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentObservation.ObservationResponse;
import com.maxlore.edumanage.Models.TeacherModels.StudentRoleResponse;
import com.maxlore.edumanage.Models.TeacherModels.TeacherRoleResponse;
import com.maxlore.edumanage.Models.TeacherModels.TimeTable.TimeTableResponse;
import com.maxlore.edumanage.Models.TeacherModels.TrackTrip.TripCheckResponse;
import com.maxlore.edumanage.Models.TeacherModels.TrackTrip.TripLocationResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;


/**
 * Created by Akshay on 04-06-2016.
 */
public interface RetrofitAPIInterface {


    @POST("/registrations/sign_in/")
    @Headers({
            "Accept: application/json"
    })
    public void signIn(@Body JsonObject loginDetails, Callback<SignInResponse> status);

    // Get Dashboard Details From Server
    @GET("/employee_dashboard")
    public void getDashboardDetails(@QueryMap Map<String, String> params, Callback<DashboardResponse> dashboard);

    @GET("/employee_dashboard")
    public void getTeacherDashboardDetails(@QueryMap Map<String, String> params, Callback<DashboardResponse> dashboard);

    // Get attendance Details From Server
    @GET("/employee_student_attendance")
    public void getAttendanceDetails(@QueryMap Map<String, String> params, Callback<AttendanceResponse> attendance);

    @POST("/mark_employee_student_attendance")
    public void markAttendance(@Body JsonObject date, Callback<StatusResponseClass> attendance);

    @GET("/registrations/sign_out")
    public void logout(@QueryMap Map<String, String> params, Callback<JSONObject> logout);

    @GET("/leave_application")
    public void leaveApplication(@QueryMap Map<String, String> params, Callback<LeaveResponse> leaveResponse);

    @POST("/apply_leave")
    public void applyLeave(@Body JsonObject jsonObject, Callback<JsonObject> leaveJson);

    @POST("/update_daily_plan_status")
    public void update_daily_plan_status(@Body JsonObject jsonObject, Callback<JsonObject> leaveJson);

    @GET("/student_leave_application")
    public void studentLeaveApplication(@QueryMap Map<String, String> params, Callback<StudentLeaveResponse> leaveResponse);

    @POST("/teacher_approved_leave_application")
    public void teacherApprovedLeaveApplication(@Body JsonObject jsonObject, Callback<JsonObject> leaveJson);

    @GET("/timetable_struct")
    public void getTimeTableData(@QueryMap Map<String, String> params, Callback<TimeTableResponse> TimeTableCallback);

    @GET("/get_teaching_plans")
    public void getSession(@QueryMap Map<String, String> params, Callback<NewTeachingResponse> newTeachingResponseCallback);

    @GET("/get_detailed_plan")
    public void getYearlyplan(@QueryMap Map<String, String> params, Callback<YearSessionResponse> yearSessionResponseCallback);

    @GET("/get_detailed_plan")
    public void getMonthAcademicPlan(@QueryMap Map<String, String> params, Callback<MonthSessionResponse> monthSessionResponseCallback);

    @GET("/get_detailed_plan")
    public void getDailyAcademicPlan(@QueryMap Map<String, String> params, Callback<DailySessionResponse> dailysessionCallback);

    @GET("/personal_profile")
    public void getProfile(@QueryMap Map<String, String> params, Callback<ProfileResponse> profileResponseCallback);

    @POST("/update_info")
    public void editemail(@Body JsonObject jsonObject, Callback<JsonObject> editmail);

    @GET("/get_assignment")
    public void getAssignmentData(@QueryMap Map<String, String> params, Callback<AssignmentStructure> AssignmentCallback);

    @POST("/change_assignment_status")
    public void markAssignment(@Body JsonObject jsonObject, Callback<JSONObject> assignment);

    @POST("/send_sms")
    public void sendassignmentmessage(@Body JsonObject jsonObject, Callback<JsonObject> sendmessage);

    @POST("/get_assignment_student")
    public void getAssignmentDetail(@Body JsonObject jsonObject, Callback<StudentStructure> studentStructureCallback);

    @POST("/change_academic_plan_status")
    public void markremark(@Body JsonObject jsonObject, Callback<JsonObject> jsonObjectCallback);

    @GET("/get_schedule_slot")
    public void getstudentdetail(@QueryMap Map<String, String> params, Callback<StudentResponse> studentresponse);

    @GET("/get_class_student")
    public void getStudentInfo(@QueryMap Map<String, String> params, Callback<StudentInfoResponse> studentInfoResponseCallback);

    @POST("/send_sms")
    public void setMessage(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @GET("/get_class_student")
    public void getClassInfo(@QueryMap Map<String, String> params, Callback<StudentInfoResponse> studentInfoResponseCallback);

    @GET("/student_observation_info")
    public void getObservation(@QueryMap Map<String, String> params, Callback<ObservationResponse> observationCallback);

    @POST("/feed_student_observations")
    public void sentObservationDetails(@Body JsonObject message, Callback<ObservationResponse> observationCallback);

    @GET("/school_info")
    public void getSchoolProfile(@QueryMap Map<String, String> params, Callback<StudentInformationResponse> studentInfoResponseCallback);

    @GET("/student_leave_list")
    public void parentApplyLeave(@QueryMap Map<String, String> params, Callback<ParentLeaveResponse> parentLeaveResponseCallback);

    @GET("/parent_dashboard")
    public void getParentDashboardDetails(@QueryMap Map<String, String> params, Callback<ParentDashboardResponse> parentdashboard);

    @POST("/apply_student_leave")
    public void applyParentLeave(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @GET("/transfers_applied")
    public void getParentTransferDetails(@QueryMap Map<String, String> params, Callback<ParentTansfercertiResponse> parentTansfercertiResponseCallback);

    @POST("/create_tc")
    public void createTc(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @GET("/parent_time_table")
    public void getParentTimeTableData(@QueryMap Map<String, String> params, Callback<ParentTimeTableResponse> TimeTableCallback);

    @GET("/get_teacher_profile")
    public void getTeacherProfile(@QueryMap Map<String, String> params, Callback<ParentResponse> parentResponseCallback);

    @GET("/get_running_parent_status")
    public void checkTripRunningStatus(@QueryMap Map<String, String> params, Callback<TripCheckResponse> tripCheckResponseCallback);

    @POST("/running_parent_status")
    public void getBusCurrentLocation(@Body JsonObject message, Callback<TripLocationResponse> tripLocationResponseCallback);

    @GET("/get_syllabus")
    public void getParentSyllabus(@QueryMap Map<String, String> params, Callback<ParentSyllabusResponse> Syllabusresponse);

    @POST("/cancel_tc")
    public void canceTc(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @GET("/parent_assignment")
    public void getParentAssignment(@QueryMap Map<String, String> params, Callback<ParentAssignmentResponse> parentAssignmentResponseCallback);

    @GET("/personal_profile")
    public void getParentProfile(@QueryMap Map<String, String> params, Callback<ParentProfileResponse> parentProfileResponseCallback);

    @GET("/parent_student_observation")
    public void getStudentObservation(@QueryMap Map<String, String> params, Callback<com.maxlore.edumanage.Models.ParentModels.ParentObservation.ObservationResponse> ProfileCallback);

    @GET("/fees_structure")
    public void getFeesDetails(@QueryMap Map<String, String> params, Callback<ParentFeeResponse> parentFeeResponseCallback);

    @GET("/get_student_attendance")
    public void getParentAttendance(@QueryMap Map<String, String> params, Callback<ParentAttendance> attendanceCallback);

    @GET("/get_attendance_detail")
    public void getStudentAttendanceDetails(@QueryMap Map<String, String> params, Callback<NewAttendanceResponseParent> newAttendanceResponseParentCallback);

    @GET("/get_attendance_detail")
    public void getemployeeAttendanceDetails(@QueryMap Map<String, String> params, Callback<OwnAttResponse> ownAttResponseCallback);

    /*@GET("/get_driver_list")
    public void getDriverListForChat(Callback<ChatDriverResponse> chatDriverResponseCallback);*/

    @POST("/send_message")
    public void sentChatMessage(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @GET("/notice_boards")
    public void getNoticeBoard(@QueryMap Map<String, String> params, Callback<NoticeBoardResponse> noticeBoardResponseCallback);

    @POST("/perform_notice_boards")
    public void creteNoticeBoard(@Body JsonObject message, Callback<StatusResponseClass> jsonObjectCallback);

    @GET("/admin_dashboard")
    public void getAdminDashboardDetails(@QueryMap Map<String, String> params, Callback<AdminDashboardResponse> admindashboard);

    @GET("/get_employee_list")
    public void getAssignSubject(@QueryMap Map<String, String> params, Callback<AssignResponse> assignResponseCallback);

    @POST("/employee_subject_list")
    public void onEmployeeClick(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @POST("/employee_subject_list")
    public void onSubjectDetails(@Body JsonObject message, Callback<AssignSubjectResponse> assignSubjectResponseCallback);

    @POST("/assign_new_subject")
    public void assignData(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @GET("/get_class_teacher")
    public void getClassteacher(@QueryMap Map<String, String> params, Callback<ClassTeacherResponse> classTeacherResponseCallback);


    @GET("/get_department_heads")
    public void getdepthead(@QueryMap Map<String, String> params, Callback<DepartmentHeadResponse> departmentHeadResponseCallback);


    @GET("/list_available_teacher")
    public void getTeacherlist(@QueryMap Map<String, String> params, Callback<Teacherlist> teacherlistCallback);

    @POST("/assign_section_teacher")
    public void assignclassTeacher(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @POST("/assign_department_head")
    public void assigndepartmenthead(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @POST("/manage_section_teacher")
    public void editdeleteTeacher(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @POST("/manage_department_head")
    public void managedepartmenthead(@Body JsonObject message, Callback<JsonObject> jsonObjectCallback);

    @POST("/attendance_timetable_list")
    public void getDepartmentemployee(@Body JsonObject message, Callback<EmployeeAttendanceResponse> employeeAttendanceResponseCallback);

    @POST("/attendance_timetable_list")
    public void getClassstudent(@Body JsonObject message, Callback<StudentAttendanceResponse> studentAttendanceResponseCallback);

    @GET("/employee_student_attendance")
    public void getempstuattendance(@QueryMap Map<String, String> params, Callback<TeachersResponse> teachersResponseCallback);

    @POST("/mark_employee_student_attendance")
    public void markEmployeeAttendance(@Body JsonObject date, Callback<StatusResponseClass> attendance);

    @GET("/employee_student_attendance")
    public void getStudentAdminAttendanceDetails(@QueryMap Map<String, String> params, Callback<StudentsResponse> studentsResponseCallback);

    /*@POST("/mark_employee_student_attendance")
    public void markEmployeeStuAttendance(@Body JsonObject date, Callback<JSONObject> attendance);*/

    @POST("/attendance_timetable_list")
    public void classtimeTableData(@Body JsonObject message, Callback<ClassTimeTableResponse> classTimeTableResponseCallback);

    @POST("/attendance_timetable_list")
    public void teachertimeTableData(@Body JsonObject message, Callback<TeacherTimeTableResponse> teacherTimeTableResponseCallback);

    @POST("/timetable_detail")
    public void getTeacherTimeTableDetails(@Body JsonObject message, Callback<TeacherTimeTableDetailResponse> teacherTimeTableDetailResponseCallback);

    @POST("/timetable_detail")
    public void getClassTimeTableDetails(@Body JsonObject message, Callback<ClassTimeTableDetailResponse> classTimeTableDetailResponseCallback);

    /*@POST("/timetable_detail")
    public void getAdminTimeTableData(@Body JsonObject jsonObject, Callback<TeacherTimeTableDetailResponse> TimeTableCallback);*/
    @GET("/employee_absent_list")
    public void getAbsentTeacherDetail(@QueryMap Map<String, String> params, Callback<AbsentResponse> absentResponseCallback);

    @POST("/all_daily_plan")
    public void secondpagedata(@Body JsonObject date, Callback<DailyTopicResponse> dailyTopicResponseCallback);

    @POST("/create_daily_plan")
    public void createDailyplan(@Body JsonObject date, Callback<DailyTopicResponse> dailyTopicResponseCallback);

    @GET("/payment_history")
    public void getFeeType(@QueryMap Map<String, String> params, Callback<FeeHistoryResponse> feeHistoryResponseCallback);

    @GET("/payment_history")
    public void getPaymentdataforparent(@QueryMap Map<String, String> params, Callback<PayHistoryResponse> payHistoryResponseCallback);

    @POST("/term_fees")
    public void paymentHistoryMethod(@Body JsonObject date, Callback<PaymentHistoryResponse> paymentHistoryResponseCallback);

    @GET("/employee_leave_list")
    public void getLeaveApplicant(@QueryMap Map<String, String> params, Callback<EditLeaveResponse> leaveResponseCallback);

    @POST("/approve_leave_application")
    public void approveLeave(@Body JsonObject jsonObject, Callback<JsonObject> jsonObjectCallback);

    @GET("/holiday_list")
    public void getHolidayDetails(@QueryMap Map<String, String> params, Callback<HolidayResponse> holidayResponseCallback);

    @POST("/create_holiday")
    public void createHoliday(@Body JsonObject jsonObject, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/manage_holiday")
    public void UpdateData(@Body JsonObject jsonObject, Callback<StatusResponseClass> jsonObjectCallback);

    @POST("/get_today_timetable")
    public void SubstituteTeacherClasses(@Body JsonObject jsonObject, Callback<SubstituteSubjectResponse> substituteSubjectResponseCallback);

    @POST("/get_subtitute_teacher")
    public void TeacherList(@Body JsonObject jsonObject, Callback<SubstituteTeacherResponse> substituteTeacherResponseCallback);

    @POST("/create_teacher_substitute")
    public void SubTeacher(@Body JsonObject jsonObject, Callback<JsonObject> jsonObjectCallback);

    @POST("/manage_student_leave")
    public void Updateleave(@Body JsonObject jsonObject, Callback<JsonObject> jsonObjectCallback);

    @GET("/students_absent_list_dash")
    public void getStudentAbsentlist(@QueryMap Map<String, String> params, Callback<AdminAbsentStudentResponse> adminAbsentStudentResponseCallback);

    @GET("/tc_list_dash")
    public void getStudentTclist(@QueryMap Map<String, String> params, Callback<AdminTcResponse> adminTcResponseCallback);

    @GET("/resignation_list_dash")
    public void getResignationlist(@QueryMap Map<String, String> params, Callback<AdminResignationResponse> adminResignationResponseCallback);

    @GET("/employees_absent_list_dash")
    public void getTeacherAbsentlist(@QueryMap Map<String, String> params, Callback<AdminTeacherAbsentResponse> adminTeacherAbsentResponseCallback);

    @GET("/track_buses")
    public void getbusesfortracking(@QueryMap Map<String, String> params, Callback<TrackResponse> trackResponseCallback);

    @POST("/send_message")
    public void sendmess(@Body JsonObject jsonObject, Callback<JsonObject> jsonObjectCallback);

    @GET("/track_bus")
    public void getsinglebustracking(@QueryMap Map<String, String> params, Callback<BusLocationResponse> busLocationResponseCallback);

    @GET("/events_for_calendar_admin")
    public void getCalenderDetails(@QueryMap Map<String, String> params, Callback<AdminCalenderResponse> adminCalenderResponseCallback);

    @GET("/events_for_calendar_others")
    public void getCalenderOthersDetails(@QueryMap Map<String, String> params, Callback<AdminCalenderResponse> adminCalenderResponseCallback);

    @GET("/dashboard_calendar")
    public void getCalenderDate(@QueryMap Map<String, String> params, Callback<CalenderDateResponse> calenderDateResponseCallback);

    @GET("/get_students_for_parent")
    public void getparentroll(@QueryMap Map<String, String> params, Callback<StudentRoleResponse> studentRoleResponseCallback);

    @GET("/get_payment_histories")
    public void getpaymentdata(@QueryMap Map<String, String> params, Callback<AdminPaymentHistoryModel> adminPaymentHistoryModelCallback);

    @GET("/get_defaulters")
    public void getdefaulterdata(@QueryMap Map<String, String> params, Callback<DefaulterResponseModel> defaulterResponseModelCallback);

    @GET("/get_fees_filters")
    public void getfeesfilterdata(@QueryMap Map<String, String> params, Callback<FiltersResponse> filtersResponseCallback);

    @GET("/school_fee_types")
    public void getschoolfeestype(@QueryMap Map<String, String> params, Callback<SchoolFeeTypeResponse> schoolFeeTypeResponseCallback);

    @GET("/fine_details")
    public void getfinesdata(@QueryMap Map<String, String> params, Callback<FineDataResponse> fineDataResponseCallback);

    @GET("/bus_fee_details")
    public void getbusdata(@QueryMap Map<String, String> params, Callback<BusFeeResponse> busFeeResponseCallback);

    @GET("/academic_fee_details")
    public void getacademicfeedata(@QueryMap Map<String, String> params, Callback<AcademicFeeResponse> academicFeeResponseCallback);

    @POST("/pay_fees")
    public void sendpayment(@Body JsonObject jsonObject, Callback<JsonObject> jsonObjectCallback);

    @GET("/academic_and_bus_fee_details")
    public void getacademicandbusfeedata(@QueryMap Map<String, String> params, Callback<AcademicBusResponse> academicBusResponseCallback);

    @GET("/get_roles_for_employee")
    public void getteacherrole(@QueryMap Map<String, String> params, Callback<TeacherRoleResponse> teacherRoleResponseCallback);

    @GET("/get_branches_for_admin")
    public void getadminbranches(@QueryMap Map<String, String> params, Callback<AdminBranches> adminBranchesCallback);
}
