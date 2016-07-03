package graduation.project.exhibition.http;

import java.util.List;

/**
 * 列表型的响应
 */
public class ListResponse<T> {

    private List<T> response;

    public void setResponse(List<T> response) {
        this.response = response;
    }

    public List<T> getResponse() {
        return this.response;
    }

}
