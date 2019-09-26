package com.stms.Responses;

 public  class FeedbackResponse {

    String status;
    public String getStatus() {
        return status;
    }

     public FeedbackResponse.data[] getData() {
         return data;
     }

     public void setData(FeedbackResponse.data[] data) {
         this.data = data;
     }

     data[] data;

     public class data {




         public String getQuestion() {
             return question;
         }

         public void setQuestion(String question) {
             this.question = question;
         }

         String question;

         public String getQid() {
             return qid;
         }

         public void setQid(String qid) {
             this.qid = qid;
         }

         String qid;

         String taskId;
         String option;
         String feedbackId;

         public String getTaskId() {
             return taskId;
         }

         public void setTaskId(String taskId) {
             this.taskId = taskId;
         }

         public String getOption() {
             return option;
         }

         public void setOption(String option) {
             this.option = option;
         }

         public String getFeedbackId() {
             return feedbackId;
         }

         public void setFeedbackId(String feedbackId) {
             this.feedbackId = feedbackId;
         }

         public String getDescription() {
             return description;
         }

         public void setDescription(String description) {
             this.description = description;
         }

         public String getRating() {
             return rating;
         }

         public void setRating(String rating) {
             this.rating = rating;
         }

         public String getFeedbackDate() {
             return feedbackDate;
         }

         public void setFeedbackDate(String feedbackDate) {
             this.feedbackDate = feedbackDate;
         }

         String description;
         String rating;
         String feedbackDate;




     }

     }

