package com.Ridoh.ExpenseTrackerApplication.Util;

import java.util.Random;

public class ResponseUtil {
    public static final String USER_EXIST_CODE="001";
    public static final String USER_EXIST_MESSAGE="User provided email already exist";
    public static final String USER_SUCCESS_CODE="002";
    public static final String USER_UPDATE_CODE="006";
    public static final String BUDGET_SUCCESS_CODE="003";
    public static final String CATEGORY_SUCCESS_CODE="004";
    public static final String EXPENSE_SUCCESS_CODE="005";
    public static final String USER_SUCCESS_MESSAGE="User successfully created";
    public static final String BUDGET_SUCCESS_MESSAGE="Budget successfully created";
    public static final String CATEGORY_SUCCESS_MESSAGE="Category successfully created";
    public static final String EXPENSE_SUCCESS_MESSAGE="Expense successfully created";
    public static final String USER_UPDATE_MESSAGE="User successfully Updated";
    public static final String USER_FETCH_LIST_MESSAGE="User successfully fetched";
    public static final String USER_FETCH_LIST_CODE="009";
    public static final String USER_ID_NOT_FOUND_CODE="003";
    public static final String USER_NOT_FOUND_CODE="007";
    public static final String USER_ID_FOUND_CODE="010";
    public static final String BUDGET_NOT_FOUND_CODE="011";
    public static final String BUDGET_UPDATE_CODE="012";
    public static final String BUDGET_EXIST_CODE="013";
    public static final String BUDGET_EXIST_MESSAGE="Kindly update your budget if exhausted or if not up to your expense amount," +
            " the provided categoryId already exist with the userId";
    public static final String BUDGET_UPDATE_MESSAGE="Budget updated successfully";
    public static final String BUDGET_NOT_FOUND_MESSAGE="Budget not found for user in this category";
    public static final String USERID_NOT_FOUND_MESSAGE="User id does not exist";
    public static final String USERID_FOUND_MESSAGE="User id exist";
    public static final String EXPENSE_WARNING_CODE="008";
    public static final String EXPENSE_WARNING_MESSAGE="Expense amount exceeds remaining budget for this category, " +
            "kindly update your budget";
}
