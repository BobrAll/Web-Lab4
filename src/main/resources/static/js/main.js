var form = new Vue({
    el: '#input-form',
    methods: {
        clear() {
            hitHelper.deleteHits();
        },
        sendHit() {
            if (this.validate()) {
                hitHelper.sendHit(this.$refs.x.value, this.$refs.y.value, this.$refs.r.value)
            }
        },
        handleR() {
            table.reloadTable();
            document.getElementById("graph-img").src =
                (this.$refs.r.value >= 0) ? "/images/graph.svg" : "/images/inverted_graph.svg";
        },
        validate() {
            const minX = -4;
            const maxX = 4;
            const minY = -4;
            const maxY = 4;
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
    }
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
        sendHit: function(event) {
            let r = form.$refs.r.value;
            let x = ((event.clientX - this.posX - this.width / 2) * Math.abs(r));
            let y = (-(event.clientY - this.posY - this.height / 2) * Math.abs(r));

            hitHelper.sendHit(hitHelper.pixelsToCoordinate(x), hitHelper.pixelsToCoordinate(y), r)
        },
        drawHits: function() {
            let context = this.$refs.graph.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);

            let hits = table.hits;
            if (hits) {
                for (let hit of hits) {
                    if(hit.r != form.$refs.r.value) continue;

                    context.beginPath();
                    context.fillStyle = hit.success? '#3d9470' : '#e32636';

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
    }
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
                .then(table.reloadTable);
        },
        deleteHits() {
            axios
                .delete("/hit")
                .then(table.reloadTable);
        }
    }
})
