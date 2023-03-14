package com.rabbit.dayfilm.payment.toss.object;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TossErrorCode {
    //FailURL
    PAY_PROCESS_CANCELED("사용자에 의해 결제가 취소되었습니다."),
    PAY_PROCESS_ABORTED("결제 진행 중 승인에 실패하여 결제가 중단되었습니다."),
    REJECT_CARD_COMPANY("결제 승인이 거절되었습니다."),

    //일반 결제 SDK 에러 코드(공통)
    BELOW_ZERO_AMOUNT("금액은 0보다 커야 합니다."),
    DUPLICATED_ORDER_ID("이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요."),
    EXCEED_MAX_DUE_DATE("가상 계좌의 최대 유효만료 기간을 초과했습니다."),
    EXCEED_MAX_VALID_HOURS("가상 계좌의 최대 유효시간을 초과했습니다."),
    FORBIDDEN_REQUEST("허용되지 않은 요청입니다."),
    INCORRECT_FAIL_URL_FORMAT("잘못된 failUrl 입니다."),
    INCORRECT_SUCCESS_URL_FORMAT("잘못된 successUrl 입니다."),
    INVALID_BANK("유효하지 않은 은행입니다."),
    INVALID_CARD_COMPANY("유효하지 않은 카드사입니다."),
    INVALID_CARD_INSTALLMENT_PLANS_WITH_MAX_AND_SINGLE("cardInstallmentPlan과 maxCardInstallmentPlan은 같이 사용할 수 없습니다."),
    INVALID_CUSTOMER_KEY("고객키는 영문 대소문자, 숫자, 특수문자 -, _, =, ., @로 2자 이상 255자 이하여야 합니다."),
    INVALID_DATE("날짜 데이터가 잘못 되었습니다."),
    INVALID_EASY_PAY("간편결제 입력정보가 올바르지 않습니다."),
    INVALID_EMAIL("이메일 주소 형식에 맞지 않습니다."),
    INVALID_FLOW_MODE_PARAMETERS("인증 창을 먼저 띄우려면 카드사 코드 또는 은행 코드 또는 간편결제 수단이 같이 전달되어야 합니다."),
    INVALID_MINOR_UNIT_FOR_CURRENCY	("금액의 소수점 자릿수를 통화 기준에 맞춰주세요."),
    INVALID_ORDER_ID("orderId는 영문 대소문자, 숫자, 특수문자(-, _) 만 허용합니다. 6자 이상 64자 이하여야 합니다."),
    INVALID_ORDER_NAME("주문 이름은 100자 이하여야 합니다."),
    INVALID_PHONE("전화번호 형식에 맞지 않습니다. 전화번호에는 특수문자가 포함될 수 없습니다."),
    INVALID_SUCCESS_URL("successUrl 값은 필수 값입니다."),
    INVALID_URL("url 은 http, https 를 포함한 주소 형식이어야 합니다."),
    INVALID_VALID_HOURS_WITH_DUE_DATE_AND_SINGLE("validHours와 dueDate는 같이 사용할 수 없습니다."),
    USER_CANCEL("취소되었습니다"),

    //결제 생성
    NOT_SUPPORTED_METHOD("지원되지 않는 결제 수단입니다."),
    INVALID_REQUEST("잘못된 요청입니다."),

    //결제 승인
    ALREADY_PROCESSED_PAYMENT("이미 처리된 결제 입니다."),
    PROVIDER_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    EXCEED_MAX_CARD_INSTALLMENT_PLAN("설정 가능한 최대 할부 개월 수를 초과했습니다."),
    NOT_ALLOWED_POINT_USE("포인트 사용이 불가한 카드로 카드 포인트 결제에 실패했습니다."),
    INVALID_API_KEY("잘못된 시크릿키 연동 정보 입니다."),
    INVALID_REJECT_CARD("카드 사용이 거절되었습니다. 카드사 문의가 필요합니다."),
    BELOW_MINIMUM_AMOUNT("신용카드는 결제금액이 100원 이상, 계좌는 200원이상부터 결제가 가능합니다."),
    INVALID_CARD_EXPIRATION	("카드 정보를 다시 확인해주세요. (유효기간)"),
    INVALID_STOPPED_CARD("정지된 카드 입니다."),
    EXCEED_MAX_DAILY_PAYMENT_COUNT("하루 결제 가능 횟수를 초과했습니다."),
    NOT_SUPPORTED_INSTALLMENT_PLAN_CARD_OR_MERCHANT("할부가 지원되지 않는 카드 또는 가맹점 입니다."),
    INVALID_CARD_INSTALLMENT_PLAN("할부 개월 정보가 잘못되었습니다."),
    NOT_SUPPORTED_MONTHLY_INSTALLMENT_PLAN("할부가 지원되지 않는 카드입니다."),
    EXCEED_MAX_PAYMENT_AMOUNT("하루 결제 가능 금액을 초과했습니다."),
    NOT_FOUND_TERMINAL_ID("단말기번호(Terminal Id)가 없습니다. 토스페이먼츠로 문의 바랍니다."),
    INVALID_AUTHORIZE_AUTH("유효하지 않은 인증 방식입니다."),
    INVALID_CARD_LOST_OR_STOLEN("분실 혹은 도난 카드입니다."),
    INVALID_CARD_NUMBER("카드번호를 다시 확인해주세요."),
    INVALID_UNREGISTERED_SUBMALL("등록되지 않은 서브몰입니다. 서브몰이 없는 가맹점이라면 안심클릭이나 ISP 결제가 필요합니다."),
    NOT_REGISTERED_BUSINESS("등록되지 않은 사업자 번호입니다."),
    EXCEED_MAX_ONE_DAY_WITHDRAW_AMOUNT("1일 출금 한도를 초과했습니다."),
    EXCEED_MAX_ONE_TIME_WITHDRAW_AMOUNT("1회 출금 한도를 초과했습니다."),
    CARD_PROCESSING_ERROR("카드사에서 오류가 발생했습니다."),
    EXCEED_MAX_AMOUNT("거래금액 한도를 초과했습니다."),
    INVALID_ACCOUNT_INFO_RE_REGISTER("유효하지 않은 계좌입니다. 계좌 재등록 후 시도해주세요."),
    UNAUTHORIZED_KEY("인증되지 않은 시크릿 키 혹은 클라이언트 키 입니다."),
    REJECT_ACCOUNT_PAYMENT("잔액부족으로 결제에 실패했습니다."),
    REJECT_CARD_PAYMENT("한도초과 혹은 잔액부족으로 결제에 실패했습니다."),
    REJECT_TOSSPAY_INVALID_ACCOUNT("선택하신 출금 계좌가 출금이체 등록이 되어 있지 않아요. 계좌를 다시 등록해 주세요."),
    EXCEED_MAX_AUTH_COUNT("최대 인증 횟수를 초과했습니다. 카드사로 문의해주세요."),
    EXCEED_MAX_ONE_DAY_AMOUNT("일일 한도를 초과했습니다."),
    NOT_AVAILABLE_BANK("은행 서비스 시간이 아닙니다."),
    INVALID_PASSWORD("결제 비밀번호가 일치하지 않습니다."),
    INCORRECT_BASIC_AUTH_FORMAT("잘못된 요청입니다. ':' 를 포함해 인코딩해주세요."),
    NOT_FOUND_PAYMENT("존재하지 않는 결제 정보 입니다."),
    NOT_FOUND_PAYMENT_SESSION("결제 시간이 만료되어 결제 진행 데이터가 존재하지 않습니다."),
    FAILED_PAYMENT_INTERNAL_SYSTEM_PROCESSING("결제가 완료되지 않았어요. 다시 시도해주세요."),
    FAILED_INTERNAL_SYSTEM_PROCESSING("내부 시스템 처리 작업이 실패했습니다. 잠시 후 다시 시도해주세요."),
    UNKNOWN_PAYMENT_ERROR("결제에 실패했어요. 같은 문제가 반복된다면 은행이나 카드사로 문의해주세요."),
    RESTRICTED_TRANSFER_ACCOUNT("계좌는 등록 후 12시간 뒤부터 결제할 수 있습니다. 관련 정책은 해당 은행으로 문의해주세요."),

    //결제 취소
    ALREADY_CANCELED_PAYMENT("이미 취소된 결제 입니다."),
    INVALID_REFUND_ACCOUNT_INFO("환불 계좌번호와 예금주명이 일치하지 않습니다."),
    EXCEED_CANCEL_AMOUNT_DISCOUNT_AMOUNT("즉시할인금액보다 적은 금액은 부분취소가 불가능합니다."),
    INVALID_REFUND_ACCOUNT_NUMBER("잘못된 환불 계좌번호입니다."),
    NOT_MATCHES_REFUNDABLE_AMOUNT("잔액 결과가 일치하지 않습니다."),
    NOT_CANCELABLE_AMOUNT("취소 할 수 없는 금액 입니다."),
    FORBIDDEN_CONSECUTIVE_REQUEST("반복적인 요청은 허용되지 않습니다. 잠시 후 다시 시도해주세요."),
    NOT_CANCELABLE_PAYMENT("취소 할 수 없는 결제 입니다."),
    EXCEED_MAX_REFUND_DUE("환불 가능한 기간이 지났습니다."),
    NOT_ALLOWED_PARTIAL_REFUND_WAITING_DEPOSIT("입금 대기중인 결제는 부분 환불이 불가합니다."),
    NOT_ALLOWED_PARTIAL_REFUND("에스크로 주문, 현금 카드 결제일 때는 부분 환불이 불가합니다. 이외 다른 결제 수단에서 부분 취소가 되지 않을 때는 토스페이먼츠에 문의해 주세요."),
    FAILED_REFUND_PROCESS("은행 응답시간 지연이나 일시적인 오류로 환불요청에 실패했습니다."),
    FAILED_METHOD_HANDLING_CANCEL("취소 중 결제 시 사용한 결제 수단 처리과정에서 일시적인 오류가 발생했습니다."),
    FAILED_PARTIAL_REFUND("은행 점검, 해약 계좌 등의 사유로 부분 환불이 실패했습니다."),
    COMMON_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

    //카드 결제
    INVALID_CARD_PASSWORD("카드 정보를 다시 확인해주세요. (비밀번호)"),
    INVALID_CARD_IDENTITY("입력하신 주민번호/사업자번호가 카드 소유주 정보와 일치하지 않습니다."),
    INVALID_BIRTH_DAY_FORMAT("생년월일 정보는 6자리의 `yyMMdd` 형식이어야 합니다. 사업자등록번호는 10자리의 숫자여야 합니다."),
    NOT_SUPPORTED_CARD_TYPE("지원되지 않는 카드 종류입니다."),
    NOT_REGISTERED_CARD_COMPANY("카드를 사용 등록 후 이용해주세요."),
    FAILED_DB_PROCESSING("잘못된 요청 값으로 처리 중 DB 에러가 발생했습니다."),
    FAILED_CARD_COMPANY_RESPONSE("카드사에서 에러가 발생했습니다. 잠시 후 다시 시도해 주세요."),

    //가상 계좌
    INVALID_REGISTRATION_NUMBER_TYPE("유효하지 않은 등록 번호 타입입니다.");

    private final String message;

    @JsonCreator
    public static TossErrorCode fromString(String param) {
        for (TossErrorCode t : TossErrorCode.values()) {
            if (t.getMessage().equals(param)) {
                return t;
            }
        }
        throw new CustomException("카드 정보가 올바르지 않습니다.");
    }
}
