var form = new Vue({
    el: '#input-form',
    methods: {
        clear() {
            hitHelper.deleteHits()
        },
        sendHit() {
            hitHelper.sendHit(this.$refs.x.value, this.$refs.y.value, this.$refs.r.value)
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
            let x = ((event.clientX - this.posX - this.width / 2) * r);
            let y = (-(event.clientY - this.posY - this.height / 2) * r);

            hitHelper.sendHit(hitHelper.pixelsToCoordinate(x), hitHelper.pixelsToCoordinate(y), r)
        },
        drawHits: function() {
            let context = this.$refs.graph.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);

            let hits = table.hits;
            if (hits) {
                for (let hit of hits) {
                    context.beginPath();
                    context.fillStyle = hit.hit? '#1a2edb' : '#e32636';

                    context.arc(this.width / 2 + hit.x * hitHelper.coordinateToPixels(hit.r),
                        this.height / 2 - hit.y * hitHelper.coordinateToPixels(hit.r),
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
