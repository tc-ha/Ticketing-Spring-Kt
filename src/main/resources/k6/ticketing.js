import http from "k6/http";
import { check } from "k6";
import { Trend, Counter } from "k6/metrics";

const BASE = __ENV.BASE_URL || "http://host.docker.internal:8080";

const successTrend = new Trend("duration_success");
const failTrend = new Trend("duration_fail");
const successCount = new Counter("count_success");
const failCount = new Counter("count_fail");

// scenario A: 순간 폭주 (티켓 오픈 시뮬레이션)
export const options = {
    scenarios: {
        spike: {
            executor: 'constant-vus',
            vus: 100,  // 동시 사용자
            duration: '30s',
        },
    },
};

export default function () {
    let userId = `user-${__VU}-${__ITER}`; // 고유한 유저 ID
    let res = http.post(`${BASE}/tickets/buy?userId=${userId}`);

    check(res, {
        "bought ticket": (r) => r.status === 200 && r.body === "SUCCESS"
    });

    if (res.body === "SUCCESS") {
        successTrend.add(res.timings.duration);
        successCount.add(1);
    } else {
        failTrend.add(res.timings.duration);
        failCount.add(1);
    }

    // sleep(Math.random());
}