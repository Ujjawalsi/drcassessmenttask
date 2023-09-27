package com.drcassessmenttask.drcassessmenttask;



    import java.time.LocalDateTime;

    public class LocalDateTimeExample {
        public static void main(String[] args) {
            LocalDateTime createdAt = LocalDateTime.of(2023, 8, 27, 12, 0); // Replace with your actual date and time

       if (createdAt.isBefore(LocalDateTime.now().minusDays(1))){
           System.out.println("pp");
       }


            System.out.println(LocalDateTime.now().minusDays(1));
        }
    }

