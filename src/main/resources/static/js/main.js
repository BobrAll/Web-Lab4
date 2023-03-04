var form = new Vue({
    el: '#input-form',
    methods: {
        clear: function () {
            axios
                .delete("/hit")
                .then(table.hits = null)
        }
    }
})

var canvas = new Vue({
    el: '#graph-canvas',
    methods: {
        sendHit: function(event) {
            let rect = this.$refs.graph.getBoundingClientRect();
            let r = form.$refs.r.value;
            let rPixels = rect.width / 7 * 3;
            let x = ((event.clientX - rect.left - rect.width / 2) / rPixels);
            let y = (-(event.clientY - rect.top - rect.height / 2) / rPixels);

            axios
                .post("/hit", {
                    x: x,
                    y: y,
                    r: r
                })
                .then(table.reloadTable)
        }
    }
})

var table = new Vue({
    el: '#hits-table',
    data: {
        hits: null
    },
    methods: {
      reloadTable: function () {
          axios
              .get("/hit")
              .then(response => this.hits = response.data)
      }
    },
    mounted() {
        this.reloadTable();
    }
})