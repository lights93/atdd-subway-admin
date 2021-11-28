package nextstep.subway.line;

import static nextstep.subway.line.LineAcceptanceTestUtils.*;
import static nextstep.subway.station.StationAcceptanceTestUtils.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.section.domain.Distance;
import nextstep.subway.section.domain.Section;
import nextstep.subway.section.domain.Sections;
import nextstep.subway.station.domain.Station;

@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {
    private Station 강남역;
    private Station 양재역;
    private Station 잠실역;
    private Station 사당역;
    private Sections 강남_양재_구간;
    private Sections 잠실_사당_구간;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = 지하철_역_등록되어_있음("강남역");
        양재역 = 지하철_역_등록되어_있음("양재역");
        잠실역 = 지하철_역_등록되어_있음("잠실역");
        사당역 = 지하철_역_등록되어_있음("사당역");

        강남_양재_구간 = new Sections(Arrays.asList(new Section(강남역, 양재역, new Distance(10))));
        잠실_사당_구간 = new Sections(Arrays.asList(new Section(잠실역, 사당역, new Distance(5))));
    }

    private Map<String, String> 지하철_신분당선() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "신분당선");
        params.put("color", "red");
        params.put("upStationId", String.valueOf(강남역.getId()));
        params.put("downStationId", String.valueOf(양재역.getId()));
        params.put("distance", "10");

        return params;
    }

    @Test
    void 지하철_노선을_생성한다() {
        // given
        Map<String, String> params = 지하철_신분당선();
        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(params);

        // then
        지하철_노선_생성됨(response);
    }

    @Test
    void 잘못된_구간길이로_지하철_노선을_생성하여_실패한다() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "신분당선");
        params.put("color", "red");
        params.put("upStationId", String.valueOf(강남역.getId()));
        params.put("downStationId", String.valueOf(양재역.getId()));
        params.put("distance", "0");

        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(params);

        // then
        지하철_노선_생성_실패됨(response);
    }

    @Test
    void 기존에_존재하는_지하철_노선_이름으로_지하철_노선을_생성한다() {
        // given
        지하철_노선_등록되어_있음("신분당선", "red", 강남역, 양재역, 10);
        Map<String, String> params = 지하철_신분당선();

        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(params);

        // then
        지하철_노선_생성_실패됨(response);
    }

    @Test
    void 지하철_노선_목록을_조회한다() {
        // given
        long id1 = 지하철_노선_등록되어_있음("신분당선", "red", 강남역, 양재역, 10);
        long id2 = 지하철_노선_등록되어_있음("2호선", "green", 잠실역, 사당역, 5);
        List<LineResponse> expected = Arrays.asList(
            LineResponse.from(new Line(id1, "신분당선", "red", 강남_양재_구간)),
            LineResponse.from(new Line(id2, "2호선", "green", 잠실_사당_구간))
        );

        // when
        ExtractableResponse<Response> response = 지하철_노선_목록_조회_요청();

        // then
        지하철_노선_목록_응답됨(response);
        지하철_노선_목록_포함됨(response, expected);
    }

    @Test
    void 지하철_노선을_조회한다() {
        // given
        long id = 지하철_노선_등록되어_있음("신분당선", "red", 강남역, 양재역, 10);
        LineResponse expected = LineResponse.from(new Line(id, "신분당선", "red", 강남_양재_구간));

        // when
        ExtractableResponse<Response> response = 지하철_노선_조회_요청(id);

        // then
        지하철_노선_응답됨(response, expected);
    }

    @Test
    void 존재하지_않는_지하철_노선을_조회하여_실패한다() {
        // given

        // when
        ExtractableResponse<Response> response = 지하철_노선_조회_요청(1L);

        // then
        지하철_노선_미존재_응답됨(response);
    }

    @Test
    void 지하철_노선을_수정한다() {
        // given
        long id = 지하철_노선_등록되어_있음("신분당선", "red", 강남역, 양재역, 10);
        Map<String, String> params = new HashMap<>();
        params.put("name", "2호선");
        params.put("color", "green");

        LineResponse expected = LineResponse.from(new Line(id, "2호선", "green", 강남_양재_구간));

        // when
        ExtractableResponse<Response> response = 지하철_노선_수정_요청(id, params);

        // then
        지하철_노선_수정됨(response, expected);
    }

    @Test
    void 존재하지_않는_지하철_노선을_수정하여_실패한다() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "2호선");
        params.put("color", "green");
        params.put("upStationId", String.valueOf(잠실역.getId()));
        params.put("downStationId", String.valueOf(사당역.getId()));
        params.put("distance", "5");

        // when
        ExtractableResponse<Response> response = 지하철_노선_수정_요청(1L, params);

        // then
        지하철_노선_미존재_응답됨(response);
    }

    @Test
    void 지하철_노선을_제거한다() {
        // given
        long id = 지하철_노선_등록되어_있음("신분당선", "red", 강남역, 양재역, 10);

        // when
        ExtractableResponse<Response> response = 지하철_노선_제거_요청(id);

        // then
        지하철_노선_삭제됨(response);
    }
}
