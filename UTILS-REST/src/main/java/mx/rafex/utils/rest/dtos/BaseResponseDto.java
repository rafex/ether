package mx.rafex.utils.rest.dtos;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import mx.rafex.utils.json.JsonUtils;

public class BaseResponseDto implements Serializable {

    private static final long serialVersionUID = 8023644832677222940L;

    @Expose
    private final String code;
    @Expose
    private final Object object;
    @Expose
    private final String message;
    @Expose
    private final String exception;

    private BaseResponseDto(final Builder builder) {
        code = builder.code;
        object = builder.object;
        message = builder.message;
        exception = builder.exception;
    }

    public String getCode() {
        return code;
    }

    public String aJson() {
        return JsonUtils.aJsonExcludeFieldsWithoutExposeAnnotation(this);
    }

    @Override
    public String toString() {
        return aJson();
    }

    public static class Builder {
        private String code;
        private Object object;
        private String message;
        private String exception;

        public Builder() {
            code = "200";
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder exception(final String exception) {
            this.exception = exception;
            return this;
        }

        public Builder code(final String code) {
            this.code = code;
            return this;
        }

        public Builder code(final Integer code) {
            this.code = code.toString();
            if (code.intValue() == 200) {
                message = "successful";
            }
            return this;
        }

        public Builder object(final Object object) {
            this.object = object;
            return this;
        }

        public BaseResponseDto build() {
            return new BaseResponseDto(this);
        }
    }

}
