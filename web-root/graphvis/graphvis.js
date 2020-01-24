//******************************************************************************
// Graph visualization 
//******************************************************************************
class GraphVis {
  constructor(graph, container) {
      this.graph = graph;
      this.init(container);
  };
  centerGraph() {
    var width = window.innerWidth;
    var height = Math.max(window.innerHeight - 100, 10);
    this.network.moveTo({
        position: {x: 0, y: 0},
        offset: {x: -width / 2, y: -height / 2},
        scale: 1
    });
  };
  getNodePositions() {
    var nodePos = this.network.getPositions();
    console.log("pos", nodePos);
    return nodePos;
  };
  getGraph() {
    var nodePos = this.network.getPositions();
    _.each(this.graph.nodes, function(node) { node.x = nodePos[node.id].x; node.y = nodePos[node.id].y;} );
    return this.graph;
  };
  init(container) {
    var self = this;
    this.container = container;
    console.log("graph", this.graph);
    this.nodes = new vis.DataSet(this.graph.nodes);
    this.edges = new vis.DataSet(this.graph.edges);
    // create a network
    var data = {
      nodes: this.nodes,
      edges: this.edges,
    };
    var options = {
      nodes: {
        borderWidth:4,
        size:30,
        color: {
          border: '#222222',
          background: '#666666'
        },
      },
      edges: {
        smooth: false,
        color: {
          inherit: false,
          highlight: 'pink',                    
          hover: '#adff29',
        },
      },
      interaction: {hover: true},
      // layout: {
      //   hierarchical: {
      //     direction: "LR",
      //   }
      // },                
    };
    this.network = new vis.Network(self.container, data, options);

    this.network.on("dragEnd", function(params) {
        for (var i = 0; i < params.nodes.length; i++) {
            var nodeId = params.nodes[i];
            self.nodes.update({id: nodeId, fixed: {x: true, y: true}});
        }
    });
    this.network.on('dragStart', function(params) {
        for (var i = 0; i < params.nodes.length; i++) {
            var nodeId = params.nodes[i];
            self.nodes.update({id: nodeId, fixed: {x: false, y: false}});
        }
    });


    // // other events
    // this.network.on("click", function (params) {
    //     params.event = "[original event]";
    //     document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
    //     console.log('click event, getNodeAt returns: ' + this.getNodeAt(params.pointer.DOM));
    // });
    // this.network.on("doubleClick", function (params) {
    //     params.event = "[original event]";
    //     document.getElementById('eventSpan').innerHTML = '<h2>doubleClick event:</h2>' + JSON.stringify(params, null, 4);
    // });
    // this.network.on("oncontext", function (params) {
    //     params.event = "[original event]";
    //     document.getElementById('eventSpan').innerHTML = '<h2>oncontext (right click) event:</h2>' + JSON.stringify(params, null, 4);
    // });
    // this.network.on("dragging", function (params) {
    //     params.event = "[original event]";
    //     document.getElementById('eventSpan').innerHTML = '<h2>dragging event:</h2>' + JSON.stringify(params, null, 4);
    // });
    // this.network.on("controlNodeDragging", function (params) {
    //     params.event = "[original event]";
    //     document.getElementById('eventSpan').innerHTML = '<h2>control node dragging event:</h2>' + JSON.stringify(params, null, 4);
    // });
    // this.network.on("controlNodeDragEnd", function (params) {
    //     params.event = "[original event]";
    //     document.getElementById('eventSpan').innerHTML = '<h2>control node drag end event:</h2>' + JSON.stringify(params, null, 4);
    //     console.log('controlNodeDragEnd Event:', params);
    // });
    // this.network.on("zoom", function (params) {
    //     document.getElementById('eventSpan').innerHTML = '<h2>zoom event:</h2>' + JSON.stringify(params, null, 4);
    // });
    // this.network.on("showPopup", function (params) {
    //     document.getElementById('eventSpan').innerHTML = '<h2>showPopup event: </h2>' + JSON.stringify(params, null, 4);
    // });
    // this.network.on("hidePopup", function () {
    //     console.log('hidePopup Event');
    // });
    // this.network.on("select", function (params) {
    //     console.log('select Event:', params);
    // });
    // this.network.on("selectNode", function (params) {
    //     console.log('selectNode Event:', params);
    // });
    // this.network.on("selectEdge", function (params) {
    //     console.log('selectEdge Event:', params);
    // });
    // this.network.on("deselectNode", function (params) {
    //     console.log('deselectNode Event:', params);
    // });
    // this.network.on("deselectEdge", function (params) {
    //     console.log('deselectEdge Event:', params);
    // });
    // this.network.on("hoverNode", function (params) {
    //     console.log('hoverNode Event:', params);
    // });
    // this.network.on("hoverEdge", function (params) {
    //     console.log('hoverEdge Event:', params);
    // });
    // this.network.on("blurNode", function (params) {
    //     console.log('blurNode Event:', params);
    // });
    // this.network.on("blurEdge", function (params) {
    //     console.log('blurEdge Event:', params);
    // });
  }
};
