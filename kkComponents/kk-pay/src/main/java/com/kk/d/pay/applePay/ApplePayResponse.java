package com.kk.d.pay.applePay;

import java.util.List;

/**
 * 描述：todo
 *
 * @author Admin
 */
public class ApplePayResponse {

    /**
     * environment : Sandbox
     * receipt : {"in_app":[{"transaction_id":"1000000559224151","original_purchase_date":"2019-08-20 07:22:12 Etc/GMT","quantity":"1","original_transaction_id":"1000000559224151","purchase_date_pst":"2019-08-20 00:22:12 America/Los_Angeles","original_purchase_date_ms":"1566285732000","purchase_date_ms":"1566285732000","product_id":"vip20190816","original_purchase_date_pst":"2019-08-20 00:22:12 America/Los_Angeles","is_trial_period":"false","purchase_date":"2019-08-20 07:22:12 Etc/GMT"}],"adam_id":0,"receipt_creation_date":"2019-08-20 07:22:12 Etc/GMT","original_application_version":"1.0","app_item_id":0,"original_purchase_date_ms":"1375340400000","request_date_ms":"1566286008905","original_purchase_date_pst":"2013-08-01 00:00:00 America/Los_Angeles","original_purchase_date":"2013-08-01 07:00:00 Etc/GMT","receipt_creation_date_pst":"2019-08-20 00:22:12 America/Los_Angeles","receipt_type":"ProductionSandbox","bundle_id":"com.ctl.student","receipt_creation_date_ms":"1566285732000","request_date":"2019-08-20 07:26:48 Etc/GMT","version_external_identifier":0,"request_date_pst":"2019-08-20 00:26:48 America/Los_Angeles","download_id":0,"application_version":"10"}
     * status : 0
     */

    private String environment;
    private ReceiptBean receipt;
    private String status;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public ReceiptBean getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptBean receipt) {
        this.receipt = receipt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ReceiptBean {
        /**
         * in_app : [{"transaction_id":"1000000559224151","original_purchase_date":"2019-08-20 07:22:12 Etc/GMT","quantity":"1","original_transaction_id":"1000000559224151","purchase_date_pst":"2019-08-20 00:22:12 America/Los_Angeles","original_purchase_date_ms":"1566285732000","purchase_date_ms":"1566285732000","product_id":"vip20190816","original_purchase_date_pst":"2019-08-20 00:22:12 America/Los_Angeles","is_trial_period":"false","purchase_date":"2019-08-20 07:22:12 Etc/GMT"}]
         * adam_id : 0
         * receipt_creation_date : 2019-08-20 07:22:12 Etc/GMT
         * original_application_version : 1.0
         * app_item_id : 0
         * original_purchase_date_ms : 1375340400000
         * request_date_ms : 1566286008905
         * original_purchase_date_pst : 2013-08-01 00:00:00 America/Los_Angeles
         * original_purchase_date : 2013-08-01 07:00:00 Etc/GMT
         * receipt_creation_date_pst : 2019-08-20 00:22:12 America/Los_Angeles
         * receipt_type : ProductionSandbox
         * bundle_id : com.ctl.student
         * receipt_creation_date_ms : 1566285732000
         * request_date : 2019-08-20 07:26:48 Etc/GMT
         * version_external_identifier : 0
         * request_date_pst : 2019-08-20 00:26:48 America/Los_Angeles
         * download_id : 0
         * application_version : 10
         */

        private String adam_id;
        private String receipt_creation_date;
        private String original_application_version;
        private String app_item_id;
        private String original_purchase_date_ms;
        private String request_date_ms;
        private String original_purchase_date_pst;
        private String original_purchase_date;
        private String receipt_creation_date_pst;
        private String receipt_type;
        private String bundle_id;
        private String receipt_creation_date_ms;
        private String request_date;
        private String version_external_identifier;
        private String request_date_pst;
        private String download_id;
        private String application_version;
        private List<InAppBean> in_app;

        public String getAdam_id() {
            return adam_id;
        }

        public void setAdam_id(String adam_id) {
            this.adam_id = adam_id;
        }

        public String getReceipt_creation_date() {
            return receipt_creation_date;
        }

        public void setReceipt_creation_date(String receipt_creation_date) {
            this.receipt_creation_date = receipt_creation_date;
        }

        public String getOriginal_application_version() {
            return original_application_version;
        }

        public void setOriginal_application_version(String original_application_version) {
            this.original_application_version = original_application_version;
        }

        public String getApp_item_id() {
            return app_item_id;
        }

        public void setApp_item_id(String app_item_id) {
            this.app_item_id = app_item_id;
        }

        public String getOriginal_purchase_date_ms() {
            return original_purchase_date_ms;
        }

        public void setOriginal_purchase_date_ms(String original_purchase_date_ms) {
            this.original_purchase_date_ms = original_purchase_date_ms;
        }

        public String getRequest_date_ms() {
            return request_date_ms;
        }

        public void setRequest_date_ms(String request_date_ms) {
            this.request_date_ms = request_date_ms;
        }

        public String getOriginal_purchase_date_pst() {
            return original_purchase_date_pst;
        }

        public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
            this.original_purchase_date_pst = original_purchase_date_pst;
        }

        public String getOriginal_purchase_date() {
            return original_purchase_date;
        }

        public void setOriginal_purchase_date(String original_purchase_date) {
            this.original_purchase_date = original_purchase_date;
        }

        public String getReceipt_creation_date_pst() {
            return receipt_creation_date_pst;
        }

        public void setReceipt_creation_date_pst(String receipt_creation_date_pst) {
            this.receipt_creation_date_pst = receipt_creation_date_pst;
        }

        public String getReceipt_type() {
            return receipt_type;
        }

        public void setReceipt_type(String receipt_type) {
            this.receipt_type = receipt_type;
        }

        public String getBundle_id() {
            return bundle_id;
        }

        public void setBundle_id(String bundle_id) {
            this.bundle_id = bundle_id;
        }

        public String getReceipt_creation_date_ms() {
            return receipt_creation_date_ms;
        }

        public void setReceipt_creation_date_ms(String receipt_creation_date_ms) {
            this.receipt_creation_date_ms = receipt_creation_date_ms;
        }

        public String getRequest_date() {
            return request_date;
        }

        public void setRequest_date(String request_date) {
            this.request_date = request_date;
        }

        public String getVersion_external_identifier() {
            return version_external_identifier;
        }

        public void setVersion_external_identifier(String version_external_identifier) {
            this.version_external_identifier = version_external_identifier;
        }

        public String getRequest_date_pst() {
            return request_date_pst;
        }

        public void setRequest_date_pst(String request_date_pst) {
            this.request_date_pst = request_date_pst;
        }

        public String getDownload_id() {
            return download_id;
        }

        public void setDownload_id(String download_id) {
            this.download_id = download_id;
        }

        public String getApplication_version() {
            return application_version;
        }

        public void setApplication_version(String application_version) {
            this.application_version = application_version;
        }

        public List<InAppBean> getIn_app() {
            return in_app;
        }

        public void setIn_app(List<InAppBean> in_app) {
            this.in_app = in_app;
        }

        public static class InAppBean {
            /**
             * transaction_id : 1000000559224151
             * original_purchase_date : 2019-08-20 07:22:12 Etc/GMT
             * quantity : 1
             * original_transaction_id : 1000000559224151
             * purchase_date_pst : 2019-08-20 00:22:12 America/Los_Angeles
             * original_purchase_date_ms : 1566285732000
             * purchase_date_ms : 1566285732000
             * product_id : vip20190816
             * original_purchase_date_pst : 2019-08-20 00:22:12 America/Los_Angeles
             * is_trial_period : false
             * purchase_date : 2019-08-20 07:22:12 Etc/GMT
             */

            private String transaction_id;
            private String original_purchase_date;
            private String quantity;
            private String original_transaction_id;
            private String purchase_date_pst;
            private String original_purchase_date_ms;
            private String purchase_date_ms;
            private String product_id;
            private String original_purchase_date_pst;
            private String is_trial_period;
            private String purchase_date;

            public String getTransaction_id() {
                return transaction_id;
            }

            public void setTransaction_id(String transaction_id) {
                this.transaction_id = transaction_id;
            }

            public String getOriginal_purchase_date() {
                return original_purchase_date;
            }

            public void setOriginal_purchase_date(String original_purchase_date) {
                this.original_purchase_date = original_purchase_date;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getOriginal_transaction_id() {
                return original_transaction_id;
            }

            public void setOriginal_transaction_id(String original_transaction_id) {
                this.original_transaction_id = original_transaction_id;
            }

            public String getPurchase_date_pst() {
                return purchase_date_pst;
            }

            public void setPurchase_date_pst(String purchase_date_pst) {
                this.purchase_date_pst = purchase_date_pst;
            }

            public String getOriginal_purchase_date_ms() {
                return original_purchase_date_ms;
            }

            public void setOriginal_purchase_date_ms(String original_purchase_date_ms) {
                this.original_purchase_date_ms = original_purchase_date_ms;
            }

            public String getPurchase_date_ms() {
                return purchase_date_ms;
            }

            public void setPurchase_date_ms(String purchase_date_ms) {
                this.purchase_date_ms = purchase_date_ms;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getOriginal_purchase_date_pst() {
                return original_purchase_date_pst;
            }

            public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
                this.original_purchase_date_pst = original_purchase_date_pst;
            }

            public String getIs_trial_period() {
                return is_trial_period;
            }

            public void setIs_trial_period(String is_trial_period) {
                this.is_trial_period = is_trial_period;
            }

            public String getPurchase_date() {
                return purchase_date;
            }

            public void setPurchase_date(String purchase_date) {
                this.purchase_date = purchase_date;
            }
        }
    }
}
