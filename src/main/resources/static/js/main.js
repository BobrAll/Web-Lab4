var form = new Vue({
    el: '#input-form',
    data: {
        xVals: [-4, -3, -2, -1, 0, 1, 2, 3, 4],
        rVals: [-4, -3, -2, -1, 0, 1, 2, 3, 4]
    },
    methods: {
        clear() {
            hitHelper.deleteHits();
        },
        sendHit() {
            if (this.validate()) {
                hitHelper.sendHit(this.$refs.x.value, this.$refs.y.value.replace(",", "."), this.$refs.r.value)
            }
        },
        handleR() {
            table.reloadTable();
            document.getElementById("graph-img").src =
                (this.$refs.r.value >= 0) ? "/images/graph.svg" : "/images/inverted_graph.svg";
        },
        ddos() {
            let r = this.$refs.r.value;
            for (let i = 0; i < 20; i++) {
                axios
                    .post("/hit", {
                        x: hitHelper.generateRandomCoordinate() * r,
                        y: hitHelper.generateRandomCoordinate() * r,
                        r: r
                    })
            }
            table.reloadTable();
        },
        validate() {
            const minX = -4;
            const maxX = 4;
            const minY = -5;
            const maxY = 5;
            const minR = -4;
            const maxR = 4;

            let x = this.$refs.x.value;
            let y = this.$refs.y.value;
            let r = this.$refs.r.value;

            if (x < minX || x > maxX) {
                this.$refs.xError.style.display = "block";
                return false;
            }
            if (y < minY || y > maxY) {
                this.$refs.yError.style.display = "block";
                return false;
            }
            if (r < minR || r > maxR) {
                this.$refs.rError.style.display = "block";
                return false;
            }

            this.$refs.xError.style.display = "none";
            this.$refs.yError.style.display = "none";
            this.$refs.rError.style.display = "none";

            return true;
        }
    },
    template:
        `
    <div class="form-coordinates">
        <label for="x">X:</label>
        <select id="x" ref="x" name="x">
            <template v-for="xVal in xVals">
                <option :value="xVal">{{xVal}}</option>
            </template>
        </select>


        <label for="y">Y:</label>
        <input type="text" ref="y" value="1" name="y" id="y">


        <label for="r">R:</label>
        <select id="r" ref="r" name="r" v-on:change="handleR">
            <template v-for="rVal in rVals">
                <option :value="rVal">{{rVal}}</option>
            </template>
        </select>

        <label ref="xError" class="errorMessage" for="x">X должен быть в пределах [-4;4]</label>
        <label ref="yError" class="errorMessage" for="y">Y должен быть в пределах [-5;5]</label>
        <label ref="rError" class="errorMessage" for="r">R должен быть в пределах [-4;4]</label>
    </div>

    <div class="form-buttons">
        <input type="button" value="отправить" v-on:click="sendHit">
        <input type="button" id="clear" value="очистить" v-on:click="clear">
        <input type="button" value="20 точек" v-on:click="ddos">
    </div>
    `
})

var canvas = new Vue({
    el: '#graph-canvas',
    data: {
        width: null,
        height: null,
        posX: null,
        posY: null,
    },
    mounted() {
        let rect = this.$refs.graph.getBoundingClientRect();
        this.width = rect.width;
        this.height = rect.height;
        this.posX = rect.left;
        this.posY = rect.top;
    },
    methods: {
        sendHit: function (event) {
            let r = form.$refs.r.value;
            let x = ((event.clientX - this.posX - this.width / 2) * Math.abs(r));
            let y = (-(event.clientY - this.posY - this.height / 2) * Math.abs(r));

            hitHelper.sendHit(hitHelper.pixelsToCoordinate(x), hitHelper.pixelsToCoordinate(y), r)
        },
        drawHits: function () {
            let context = this.$refs.graph.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);

            let hits = table.hits;
            if (hits) {
                for (let hit of hits) {
                    if (hit.r != form.$refs.r.value) continue;

                    context.beginPath();
                    context.fillStyle = hit.success ? '#3d9470' : '#e32636';

                    context.arc(this.width / 2 + hit.x * hitHelper.coordinateToPixels(Math.abs(hit.r)),
                        this.height / 2 - hit.y * hitHelper.coordinateToPixels(Math.abs(hit.r)),
                        5,
                        0,
                        Math.PI * 2,
                        true);
                    context.fill();
                }
            }
        }
    }
})

var table = new Vue({
    el: '#hits-table',
    data: {
        hits: null
    },
    mounted() {
        this.reloadTable();
    },
    methods: {
        reloadTable: function () {
            axios
                .get("/hit")
                .then(response => {
                    this.hits = response.data;
                    canvas.drawHits();
                })

        }
    },
    template:
        `
        <tr>
            <th>Date and Time</th>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Hit</th>
        </tr>
        <tr v-for="hit in hits">
            <td>{{hit.dateTime}}</td>
            <td>{{hit.x.toFixed(1)}}</td>
            <td>{{hit.y.toFixed(1)}}</td>
            <td>{{hit.r.toFixed(1)}}</td>
            <td>{{hit.success}}</td>
        </tr>
    `
})

var hitHelper = new Vue({
    methods: {
        pixelsToCoordinate(pixels) {
            return pixels / (canvas.width / 7 * 3);
        },
        coordinateToPixels(coordinate) {
            return (canvas.width / 7 * 3) / coordinate;
        },
        sendHit(x, y, r) {
            axios
                .post("/hit", {
                    x: x,
                    y: y,
                    r: r
                })
                .then(response => {
                    if (response.headers.get("content-type") == "text/html;charset=UTF-8")
                        document.write(response.data)

                    table.reloadTable();
                })
        },
        deleteHits() {
            axios
                .delete("/hit")
                .then(table.reloadTable);
        },
        generateRandomCoordinate() {
            return (Math.random() - 0.5) * 2.3;
        }
    }
})
