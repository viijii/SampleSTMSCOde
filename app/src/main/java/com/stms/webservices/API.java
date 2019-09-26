package com.stms.webservices;




import com.stms.Responses.FeedbackResponse;
import com.stms.Responses.FieldActivityResponse;
import com.stms.Responses.Get_Profile_response;
import com.stms.Responses.QuotationsResponse;
import com.stms.Responses.UpdateResponse;
import com.stms.Responses.otpRes;
import com.stms.Responses.otpValRes;
import com.stms.Responses.Add_Employee_response;
import com.stms.Responses.AdminApprovedSpinnerDialogResponse;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.Responses.CommonResponse;
import com.stms.Responses.EditVehicleResponse;
import com.stms.Responses.LoginResponse;
import com.stms.Responses.ManageTaskPendingResponse;
import com.stms.Responses.MyTaskResponse;
import com.stms.Responses.Spinner_response;
import com.stms.Responses.UserResponse;
import com.stms.Responses.VehicalResponse;
import com.stms.Responses.ViewCustomerResponse;
import com.stms.Responses.addEditEmployeeResponse;
import com.stms.Responses.customerResponse;
import com.stms.Responses.view_VehicleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface API
{
    @GET("userlogin.php") //login
    Call<LoginResponse> login(
            @Query("username") String username,
            @Query("password") String password,
            @Query("corp_code") String corpcode,
            @Query( "utype" )String utype
    );
    @GET("getCustomerDetails.php")//getting customer details
    Call<customerResponse> getCustomers(@Query("empId") String user_id,
                                        @Query("corp_code") String corp_code

    );
    @GET("getVehicles.php")//getting vehicle details
    Call<VehicalResponse> getVehicle(@Query( "action" ) String get,
                                     @Query( "corp_code" )String crop_code
    );
    @GET("getEmployees.php")//getting employee details
    Call<UserResponse> getUsers(@Query("empId") String user_id,
                                @Query("corp_code") String corp_code,
                                @Query("action") String action);

    @GET("spinner.php")
    Call<Spinner_response> getcustomer(@Query( "action" ) String customer,
                                       @Query("corp_code") String corp_code);

    @GET("spinner.php")
    Call<Spinner_response> getVtype(@Query( "action" ) String vehicle,
                                    @Query("corp_code") String corp_code);

    @GET("save_vehicle.php")//inserting vehicle details
    Call<Add_Employee_response> submit(@Query( "action" ) String save,
                                       @Query( "vId" ) String toString,
                                       @Query( "customer" ) String customer1,
                                       @Query( "vtype" ) String vtype1,
                                       @Query( "vehModel" ) String vehModel,
                                       @Query( "tID" ) String toString1,
                                       @Query( "tPwd" ) String toString2,
                                       @Query( "corp_code" )String crop_code,
                                       @Query( "next_service" ) String next,
                                       @Query( "purchase_date" ) String purchase,
                                       @Query( "loginId" ) String lid
    );

    @GET("spinner.php")//getting models
    Call<Spinner_response> getVehModel(@Query("action") String model,
                                       @Query("vehType") String vtype1,
                                       @Query("corp_code")String corp_code);

    @GET("save_vehicle.php")//getting perticular vehicle details
    Call<EditVehicleResponse> getVdata(@Query( "action" ) String save,
                                       @Query( "vId" )String vid,
                                       @Query( "corp_code" )String corp_code);

    @GET("manage_users.php")//inserting emp details
    Call<addEditEmployeeResponse> saveusers(
            @Query("action") String action,
            @Query("fullName") String fullName,
            @Query("mobileNumber") String mobileNumber,
            @Query("empId") String empId,
            @Query("city") String city,
            @Query("aadhar") String aadhar,
            @Query("email") String email,
            @Query("currentAddress") String currentAddress,
            @Query("permanentAddress") String permanentAddress,
            @Query("joiningDate") String joiningDate,
            @Query("designation") String designation,
            @Query("userName") String userName,
            @Query("password") String password,
            @Query("userStatus") String userStatus,
            @Query("userType") String userType,
            @Query("createdBy") String createdBy,
            @Query("corp_code") String corp_code
    );
    @GET("manage_users.php")
    Call<addEditEmployeeResponse> getDetails(
            @Query("action") String action,
            @Query("empId") String empId,
            @Query("corp_code") String corp_code
    );

    @GET("vehicle_view.php")
    Call<view_VehicleResponse> manageemployee(
            @Query("empId") String customerId,
            @Query("corp_code") String corp_code

    );

    @GET("employee_view.php")
    Call<view_VehicleResponse> employeeview(@Query("empId") String empId,
                                            @Query("corp_code") String corp_code);
    @GET("admin_approved.php")
    Call<AdminRequestedResponse> getapprovedIdeas(
            @Query( "action" ) String action,
            @Query( "user_type" ) String role,
            @Query("corp_code") String corp_code
    );


    @GET("customerview.php")
    Call<ViewCustomerResponse> customerview(@Query("corp_code") String crop,
                                            @Query("empId") String cId);

    @GET("getadminrequested.php")
    Call<AdminRequestedResponse> getIdeas(@Query( "action" ) String action,
                                          @Query( "role" ) String role,
                                          @Query("corp_code") String corp_code);

    @GET("getTasks.php")
    Call<MyTaskResponse> getUserTask(@Query("user_type") String user_type,
                                     @Query("action") String action,
                                     @Query("idea_id") String idea_id,
                                     @Query("corp_code") String corp_code,
                                     @Query("empId") String user_id);




    @GET("getTasks.php")
    Call<ManageTaskPendingResponse> getManageTask(
            @Query("action") String action,
            @Query("user_type") String user_type,
            @Query("corp_code") String corp_code
    );


    @GET("spinner.php")
    Call<AdminApprovedSpinnerDialogResponse> getname(@Query("action") String action,
                                                     @Query("corp_code") String corp_code);


    @GET("manage_ideas.php")
    Call<AdminApprovedSpinnerDialogResponse> datainserting(@Query("action") String action,
                                                           @Query("corp_code") String corp_code,
                                                           @Query("empId") String empId,
                                                           @Query("complaintId") String complaintId,
                                                           @Query("spinner") String spinner,
                                                           @Query("title") String title,
                                                           @Query("instructions") String instructions,
                                                           @Query("days") String estdAYS


    );

    @GET("manage_ideas.php")
    Call<AdminRequestedResponse> approveIdea(@Query( "action" ) String action,
                                             @Query("complaintId") String complaintId,
                                             @Query("corp_code") String corp_code);

    @GET("spinner.php")
    Call<AdminRequestedResponse> getSpinner(@Query( "action" ) String action,
                                            @Query("corp_code") String corp_code,
                                            @Query("customerId") String customerId);
    @GET("manage_ideas.php") // customer request service
    Call<AdminRequestedResponse> addservice(@Query( "action" ) String action,
                                            @Query("corp_code") String corp_code,
                                            @Query("customerId") String customerId,
                                            @Query("title") String title,
                                            @Query("description") String description,
                                            @Query("vehicleId") String spinner,
                                            @Query("imagePath") String imagePath
    );

    @GET("getadminrequested.php") //customer requested ideas
    Call<AdminRequestedResponse> getCustomerIdeas(@Query( "action" ) String action,
                                                  @Query( "role" ) String role,
                                                  @Query( "customerId" ) String customerId,
                                                  @Query("corp_code") String corp_code);

    @GET("manage_customer.php")  // save customers
    Call<addEditEmployeeResponse> savecustomers(
            @Query("action") String action,
            @Query("fullName") String fullName,
            @Query("mobileNumber") String mobileNumber,
            @Query("city") String city,
            @Query("aadhar") String aadhar,
            @Query("email") String email,
            @Query("currentAddress") String currentAddress,
            @Query("permanentAddress") String permanentAddress,
            @Query("userName") String userName,
            @Query("password") String password,
            @Query("userStatus") String userStatus,
            @Query("createdBy") String createdBy,
            @Query("corp_code") String corp_code
    );

    @GET("admin_approved.php")  //customer approved Ideas
    Call<AdminRequestedResponse> customerapprovedIdeas(
            @Query( "action" ) String action,
            @Query( "user_type" ) String role,
            @Query("corp_code") String corp_code,
            @Query("empId") String user_id
    );

    @GET("otp.php")// generating otp values
    Call<otpRes> generateOTP(
            @Query("idea") String idea,
            @Query( "taskId" ) String task,
            @Query( "empId" ) String empId,
            @Query("corp_code") String corp_code
    );


    @GET("valotp.php")//validating otp values
    Call<otpValRes> validateOTP(
            @Query( "action" ) String action,
            @Query("idea") String idea,
            @Query( "taskId" ) String task,
            @Query("otp") String otp,
            @Query("corp_code") String corp_code
    );

    @GET("updateOtp.php")//updating otp values
    Call<otpValRes> updateOTP(
            @Query( "taskId" ) String taskId,
            @Query("corp_code") String corp_code);

//get profile details
    @GET("getProfile.php")
    Call<Get_Profile_response> getProfileData(@Query("action") String action,
                                               @Query("empId") String empId,
                                              @Query("corp_code") String crop,
                                               @Query("user_type") String user_type);


    //update profile
    @GET("getProfile.php")
    Call<Get_Profile_response> updateProfileData(@Query("action") String action,
                                                 @Query("empId") String empId,
                                                 @Query("number") String number,
                                                 @Query("corp_code") String crop,
                                                 @Query("user_type") String user_type
    );
    @GET("updates.php")
    Call<CommonResponse> allUpdates(@Query( "action" ) String action,
                                    @Query( "offers" ) String offers,
                                    @Query( "s" ) String string,
                                    @Query("corp_code") String corp_code);
    @GET("updates.php")
    Call<UpdateResponse> getAll(@Query( "action" ) String action,
                                @Query( "role" ) String role,
                                @Query("corp_code") String corp_code);

    //delete managing details start here

    @GET("deleteDetails.php")
    Call<CommonResponse> vehicleDelete(@Query( "action" ) String action,
                                       @Query("corp_code") String corp_code,
                                       @Query( "empId" ) String vehicleId,
                                       @Query( "role" ) String vehicleType);
    @GET("deleteDetails.php")
    Call<CommonResponse> customerDelete(@Query( "action" ) String action,
                                        @Query("corp_code") String corp_code,
                                        @Query( "empId" ) String customerId,
                                        @Query( "role" ) String role);


    @GET("deleteDetails.php")
    Call<CommonResponse> deleteUser(@Query("action") String action,
                                    @Query("corp_code") String corp_code,
                                    @Query("empId") String user_id,
                                    @Query("role")  String user_type);
    //end here

    @GET("adminfeedback.php")
    Call<FeedbackResponse> feedbackForm(
            @Query("action") String action,
            @Query("taskid") String taskid,
            @Query("rating") String rating,
            @Query("comments") String comments,
            @Query("option1") String option1,
            @Query("option2") String option2,
            @Query("option3") String option3,
            @Query("id1") String id1,
            @Query("id2") String id2,
            @Query("id3") String id3,
            @Query("corp_code") String corp_code

    );

    @GET("gettingquestion.php")//getting feedback questions
    Call<FeedbackResponse> gettingquestions();


    @GET("get_ideas.php")//getting customer completed complaints
    Call<AdminRequestedResponse> getCustomercompletedIdeas(@Query( "action" ) String action,
                                                           @Query( "role" ) String role,
                                                           @Query( "customerId" ) String customerId,
                                                           @Query("corp_code") String corp_code);




    @GET("manage_tasks.php") //getting details for modify task
    Call<ManageTaskPendingResponse> ModifyTask(
            @Query("action") String action,
            @Query("taskId") String taskid,
            @Query("corp_code") String corp_code
    );

    @GET("manage_tasks.php") //update modified task details
    Call<AdminApprovedSpinnerDialogResponse> updatemodifyTask(
            @Query("action") String action,
            @Query("taskId") String taskId,
            @Query("corp_code") String corp_code,
            @Query("empId") String empId,
            @Query("complaintId") String complaintId,
            @Query("spinner") String spinner,
            @Query("title") String title,
            @Query("instructions") String instructions,
            @Query("days") String estdays
    );

    @GET("adminfeedback.php") //getting feeedback values
    Call<FeedbackResponse> gettingfeedback(@Query("action") String action,
                                           @Query("corp_code") String corp_code,
                                           @Query("taskid") String taskid);


    @GET("manage_tasks.php")//verify task
    Call<ManageTaskPendingResponse> verifyTask(@Query("action")String verify,
                                               @Query("taskId")String taskId,
                                               @Query("corp_code")String corp_code);

    // getting Quotation requested & responded data
    @GET("quotations.php")
    Call<QuotationsResponse> getReqQuotations(@Query("action")String action,
                                              @Query("user_type")String toLowerCase,
                                              @Query("corp_code") String corp_code);

    //group chat
    @GET("taskChat.php")
    Call<CommonResponse> TaskchatCount(@Query("by") String assigned_by,
                                       @Query("to") String assigned_to,
                                       @Query("corp_code") String crop,
                                       @Query("task_id") String task_id);
    @GET("field_activity.php")
    Call<FieldActivityResponse> getFields(@Query("corp_code") String crop);


}