var YAHOO=window.YAHOO||{};
YAHOO.namespace=function(_1){
        if(!_1||!_1.length){
                return null;
        }
        var _2=_1.split(".");
        var _3=YAHOO;
        for(var i=(_2[0]=="YAHOO")?1:0;i<_2.length;++i){
                _3[_2[i]]=_3[_2[i]]||{};
                _3=_3[_2[i]];
        }
return _3;
};
YAHOO.namespace("util");
YAHOO.namespace("widget");
YAHOO.namespace("example");

var YMAPPID = "euzuro-openlayers";
function _ywjs(inc) { var o='<'+'script src="'+inc+'"'+' type="text/javascript"><'+'/script>'; document.write(o); }_ywjs('http://l.yimg.com/d/lib/common/utils/2/dom_2.0.1-b2.js');
_ywjs('http://l.yimg.com/d/lib/common/utils/2/event_2.0.0-b2.js');
_ywjs('http://l.yimg.com/d/lib/common/utils/2/dragdrop_2.0.1-b4.js');
_ywjs('http://l.yimg.com/d/lib/common/utils/2/animation_2.0.1-b2.js');
_ywjs('http://l.yimg.com/d/lib/map/js/api/ymapapi_3_8_2_7.js');<!-- xm3.maps.re3.yahoo.com compressed/chunked Fri Feb  5 07:49:54 PST 2010 -->
