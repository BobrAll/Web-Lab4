var form = new Vue({
    el: '#input-form',
    methods: {
        clear: function () {
            axios
                .delete("/hit")
                .then(table.reloadTable)
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
        this.rPixels = this.width / 7 * 3;
    },
    methods: {
        sendHit: function(event) {
            let r = form.$refs.r.value;
            let rPixels = this.width / 7 * 3;
            let x = ((event.clientX - this.posX - this.width / 2) / rPixels);
            let y = (-(event.clientY - this.posY - this.height / 2) / rPixels);

            axios
                .post("/hit", {
                    x: x,
                    y: y,
                    r: r
                })
                .then(
                    table.reloadTable
                )
        },
        drawHits: function() {
            let context = this.$refs.graph.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);

            let hits = table.hits;
            if (hits) {
                for (let hit of hits) {
                    context.beginPath();
                    context.fillStyle = hit.hit? '#1a2edb' : '#e32636';
                    context.arc(this.width / 2 + hit.x * this.rPixels,
                        this.height / 2 - hit.y * this.rPixels,
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
