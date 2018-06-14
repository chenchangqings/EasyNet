package com.android.ccq.easynet.reponse;

import java.util.List;

/**
 * Created by stories on 2018/1/27.
 */

public class DogBean  extends BaseBean<DogBean>{
        /**
         * id : 1
         * skin : hhh
         * hot : 999
         */

        public int id;
        public String title;
        public String cover;
        public String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

}
