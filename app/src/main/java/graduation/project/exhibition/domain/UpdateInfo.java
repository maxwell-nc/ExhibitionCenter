package graduation.project.exhibition.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 更新信息
 */
public class UpdateInfo implements Serializable {
    private Info info;

    private List<Extend> extend;

    public void setInfo(Info info) {
        this.info = info;
    }

    public Info getInfo() {
        return this.info;
    }

    public void setExtend(List<Extend> extend) {
        this.extend = extend;
    }

    public List<Extend> getExtend() {
        return this.extend;
    }

    public class Info implements Serializable{
        private String verCode;

        private String desc;

        private String url;

        public void setVerCode(String verCode) {
            this.verCode = verCode;
        }

        public String getVerCode() {
            return this.verCode;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

    }

    public class Extend implements Serializable{
        private String type;

        private String content;

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

    }


}
