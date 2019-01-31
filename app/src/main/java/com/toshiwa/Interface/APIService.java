package com.toshiwa.Interface;

import com.toshiwa.Model.Account.AccountResponse;
import com.toshiwa.Model.Bill.BillResponse;
import com.toshiwa.Model.Completion.CompletionResponse;
import com.toshiwa.Model.Employee.DisplayEmployee;
import com.toshiwa.Model.Execution.ExecutionResponse;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Approval.ApprovalResponse;
import com.toshiwa.Model.Lead.DisplayLeadList;
import com.toshiwa.Model.Login.LoginResponse;
import com.toshiwa.Model.Material.MaterialResponse;
import com.toshiwa.Model.Meter.MeterResponse;
import com.toshiwa.Model.Notification.NotificationRes;
import com.toshiwa.Model.Purchase.PurchaseResponse;
import com.toshiwa.Model.Service.ServiceResponse;
import com.toshiwa.Model.Status.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("display_leads.php")
    Call<DisplayLeadList> display_leads(@Query("empid") String empid);

    @GET("display_profile.php")
    Call<DisplayLeadList> display_profile(@Query("empid") String empid,@Query("lid") int lid);


    @GET("display_employee.php")
    Call<DisplayEmployee> display_emp();


    @GET("delete_lead.php")
    Call<AddLeadResponse> delete_lead(@Query("lid") String lid);

    @GET("admin_purchase.php")
    Call<DisplayLeadList> admin_purchase();

    @GET("admin_approval.php")
    Call<DisplayLeadList> admin_approval();

    @GET("admin_material.php")
    Call<DisplayLeadList> admin_material();

    @GET("admin_execution.php")
    Call<DisplayLeadList> admin_execution();

    @GET("admin_completion.php")
    Call<DisplayLeadList> admin_completion();

    @GET("admin_bill.php")
    Call<DisplayLeadList> admin_bill();

    @GET("admin_account.php")
    Call<DisplayLeadList> admin_account();

    @GET("admin_meter.php")
    Call<DisplayLeadList> admin_meter();

    @GET("admin_service.php")
    Call<DisplayLeadList> admin_service();


    @FormUrlEncoded
    @POST("add_lead.php")
    Call<NotificationRes> add_lead(@Field("empid") int empid, @Field("ldate") String ldate, @Field("lname") String lname,
                                   @Field("lmobileno") String lmobileno, @Field("ldealer_name") String ldealer_name,
                                   @Field("lcapacity") String lcapacity, @Field("ldealer_contact_person") String ldealer_contact_person,
                                    @Field("llocation") String llocation,
                                   @Field("lcapex") String lcapex, @Field("lopex") String lopex);

    @FormUrlEncoded
    @POST("add_purchase.php")
    Call<AddLeadResponse> add_purchase(@Field("empid") int empid,@Field("lid") int lid, @Field("responsible_person") String responsible_person,
                                   @Field("purchase_date") String purchase_date, @Field("subsidy") String subsidy,
                                   @Field("grid") String grid, @Field("load_extenstion") String load_extenstion,
                                   @Field("phase") String phase, @Field("type") String type,
                                   @Field("project_cost") String project_cost, @Field("fabrication") String fabrication,
                                   @Field("invertor") String invertor, @Field("panel") String panel);


    @GET("display_purchase.php")
    Call<PurchaseResponse> display_purchase(@Query("lid") int lid);

    @GET("display_approval.php")
    Call<ApprovalResponse> display_approval(@Query("lid") int lid);

    @GET("display_material.php")
    Call<MaterialResponse> display_material(@Query("lid") int lid);

    @GET("display_execution.php")
    Call<ExecutionResponse> display_execution(@Query("lid") int lid);

    @GET("display_completion.php")
    Call<CompletionResponse> display_completion(@Query("lid") int lid);

    @GET("display_bill.php")
    Call<BillResponse> display_bill(@Query("lid") int lid);

    @GET("display_account.php")
    Call<AccountResponse> display_account(@Query("lid") int lid);

    @GET("display_meter_reading.php")
    Call<MeterResponse> display_meter_reading(@Query("lid") int lid);

    @GET("display_service.php")
    Call<ServiceResponse> display_service(@Query("lid") int lid);


    /******* Status API ******/
    @GET("status_purchase.php")
    Call<PurchaseResponse> purchase_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_approval.php")
    Call<ApprovalResponse> approval_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_material.php")
    Call<MaterialResponse> material_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_execution.php")
    Call<ExecutionResponse> execution_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_completion.php")
    Call<CompletionResponse> completion_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_bill.php")
    Call<BillResponse> bill_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_account.php")
    Call<AccountResponse> account_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_meter.php")
    Call<MeterResponse> meter_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_service.php")
    Call<ServiceResponse> service_status(@Query("lid") int lid, @Query("status") String status);

    @GET("status_lead.php")
    Call<StatusResponse> lead_status(@Query("lid") int lid);

    /****Status API End****/

    @FormUrlEncoded
    @POST("add_approval.php")
    Call<AddLeadResponse> add_approval(@Field("empid") int empid,@Field("lid") int lid,
                                       @Field("responsible_person") String pResponsiblePerson,
                                       @Field("approval_date") String pDate,
                                       @Field("load_extension_done") String pLoadDone,
                                       @Field("load_reflection_bill") String pLoadReflection,
                                       @Field("quotation_paid") String pQuotationDone,
                                       @Field("solar_online_application") String pSolarOnlineAppDone,
                                       @Field("solar_offline_application") String pSolarOfflineAppDone,
                                       @Field("solar_sanction_received") String pSanctionReceived,
                                       @Field("meda_application_done") String pMedaAppDone,
                                       @Field("meda_sanction_received") String pMedaSanctionReceived,
                                       @Field("joint_inspection") String pJointInspection,
                                       @Field("pcr_entered") String pPcr,
                                       @Field("funds_released") String pFundReleased,
                                       @Field("net_meter_no") String pNetMeterNo,
                                       @Field("generation_meter_no") String pGenerationMeterNo );

    @FormUrlEncoded
    @POST("add_material.php")
    Call<AddLeadResponse> add_material(@Field("empid") int empid,@Field("lid") int lid,
                                       @Field("responsible_person") String responsible_person,
                                       @Field("inverter") String inverter,
                                       @Field("module") String module,
                                       @Field("order") String order,
                                       @Field("order_details") String order_details,
                                       @Field("available") String available,
                                       @Field("available_details") String available_details,
                                       @Field("material_dispatched_onsite") String material_dispatched_onsite,
                                       @Field("dispatched_date") String dispatched_date);
    @FormUrlEncoded
    @POST("add_execution.php")
    Call<AddLeadResponse> add_execution(@Field("empid") int empid,@Field("lid") int lid,
                                       @Field("responsible_person") String responsible_person,
                                       @Field("material_ready") String material_ready,
                                       @Field("start_date") String start_date,
                                       @Field("work_completion_date") String work_completion_date,
                                       @Field("remark") String remark
                                       );
    @FormUrlEncoded
    @POST("add_completion.php")
    Call<AddLeadResponse> add_completion(@Field("empid") int empid,@Field("lid") int lid,
                                        @Field("completion_date") String completion_date
                                         );

    @FormUrlEncoded
    @POST("add_bill.php")
    Call<AddLeadResponse> add_bill(@Field("empid") int empid,@Field("lid") int lid,
                                   @Field("responsible_person") String responsible_person,
                                   @Field("meter_installation_date") String meter_installation_date,
                                   @Field("solar_tagged") String solar_tagged,
                                   @Field("solar_tagged_date") String solar_tagged_date,
                                   @Field("first_bill_received") String first_bill_received,
                                   @Field("remark") String remark);
    @FormUrlEncoded
    @POST("add_account.php")
    Call<AddLeadResponse> add_account(@Field("empid") int empid,@Field("lid") int lid,
                                   @Field("acc_count") String acc_count,
                                   @Field("po_amount") String po_amount,
                                   @Field("advance_received") String advance_received,
                                   @Field("remark") String remark
                                   );

    @FormUrlEncoded
    @POST("add_meter.php")
    Call<AddLeadResponse> add_meter(@Field("empid") int empid,@Field("lid") int lid,
                                      @Field("reading_count") String reading_count,
                                      @Field("reader_name") String reader_name,
                                      @Field("reading_date") String reading_date,
                                      @Field("export") String export,
                                      @Field("import") String immport,
                                      @Field("generation") String generation
    );

    @FormUrlEncoded
    @POST("add_service.php")
    Call<AddLeadResponse> add_service(@Field("empid") int empid,@Field("lid") int lid,
                                    @Field("responsible_person") String responsible_person,
                                    @Field("scheduled_date") String scheduled_date,
                                    @Field("call_count") String call_count,
                                    @Field("call_date") String call_date,
                                    @Field("call_remark") String call_remark,
                                    @Field("call_attend_remark") String call_attend_remark
    );

    @FormUrlEncoded
    @POST("add_employee.php")
    Call<AddLeadResponse> add_emp(@Field("name") String name,
                                  @Field("mobile") String mobile,
                                  @Field("password") String password );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> user_login(@Field("mobile") String mobile,
                                   @Field("password") String password,
                                   @Field("login") String login
                       );

}
