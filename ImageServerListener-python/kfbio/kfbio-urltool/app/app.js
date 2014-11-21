function kfbioController($scope){
	$scope.kfbio={
		protocol:'http',
		host:'112.15.174.50',
		port:'8088',
		viewerPath:'Viewer.aspx',
		thumbnailPath:'ThumnailHandler.aspx',
		barcode:'01.1_20141104_0111384416_2',
		SecretKey:'SlideSlideViewer',
		token:'',
		slideUrl:'',
		thumbnailUrl:'',
	};
	
	function caculateURL(){
		var t = $scope.kfbio;
		var md5Dec=t.barcode+"/n"+t.SecretKey;
		var md5Enc=hex_md5(md5Dec).toUpperCase();
		$scope.kfbio.token=md5Enc;
		$scope.kfbio.slideUrl=t.protocol
						 +"://"+t.host
						 +":"+t.port
						 +"/"+t.viewerPath
						 +"?barcode="+t.barcode
						 +"&token="+md5Enc;
		$scope.kfbio.thumbnailUrl=t.protocol
						 +"://"+t.host
						 +":"+t.port
						 +"/"+t.thumbnailPath
						 +"?barcode="+t.barcode;
	};
	
	$scope.$watch('kfbio',caculateURL,true);
};