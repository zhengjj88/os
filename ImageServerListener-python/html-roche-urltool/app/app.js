function rocheController($scope){
	$scope.roche={
		protocol:'http',
		host:'www.kingmed.com.cn',
		port:'8080',
		viewerPath:'virtuoso/extLink.seam',
		thumbnailPath:'',
		slideView:'true',
		UID:'virtpatho',
		token:'RocheKingMed2013',
		barcode:'01.1_20141117_0112945300_1',
		slideUrl:'',
		thumbnailUrl:'',
	};
	
	function caculateURL(){
		var t = $scope.roche;
		var UIDbase64=base64encode(t.UID);
		var tokenbase64=base64encode(t.token);
		var barcodebase64=base64encode(t.barcode);

		$scope.roche.slideUrl=t.protocol
						 +"://"+t.host
						 +":"+t.port
						 +"/"+t.viewerPath
						 +"?slideView="+t.slideView
						 +"&UID="+UIDbase64
						 +"&token="+tokenbase64
						 +"&SLID="+barcodebase64;
	};
	
	$scope.$watch('roche',caculateURL,true);
};