/**
 */
angular.module('app').controller('CouponCtrl', function($scope, $window, $log, CouponService) {
	
	/**
	 * 현재시간
	 */
	$scope.current = new Date();
	
	/**
	 * 화면 사용 변수
	 */
	$scope.views = {
			
			//이메일 입력값
			userEmail: null,
			
			//페이징 처리를 위해 페이지 조건 객체
			pageCondition: {
				
				//페이지 번호
				page: 0,
				
				//페이지 사이즈
				size: 10,
				
				//정렬 조건
				sort: 'id,asc',
				
				//현재 페이지 번호
				currentPageNumber: 0,
				
				/**
				 * 페이지 객체를 query 문자열로 변환
				 */
				query: function () {
					
					var query;
					
					if(angular.isUndefined($scope.views.pageCondition.page) || $scope.views.pageCondition.page < 0) {
						$scope.views.pageCondition.page = 0;
					}
					query = '?page=' + $scope.views.pageCondition.page;
					
					if(angular.isUndefined($scope.views.pageCondition.size) || $scope.views.pageCondition.size < 0) {
						$scope.views.pageCondition.size = 10;
					}
					query += '&size=' + $scope.views.pageCondition.size;
					
					if(angular.isUndefined($scope.views.pageCondition.sort) || $scope.views.pageCondition.sort == '') {
						$scope.views.pageCondition.sort = 'id,asc';
					}
					query += '&sort=' + $scope.views.pageCondition.sort;
					
					return query;
				},
				
				pageList: null,
				
				setPageResult: function (totalPages, currentPageNumber) {
					
					var startPage = currentPageNumber - 10;
				    	var endPage = currentPageNumber + 10;
				    	var pageArray = [];
				    	
				    	if(startPage < 0) {
				    		startPage = 0;
				    	}
				    	if(endPage > totalPages - 1) {
				    		endPage = totalPages - 1;
				    	}
				    	for(var i = startPage; i <= endPage; i++) {
				    		pageArray.push(i);
				    	}
				    	
				    	$scope.views.pageCondition.currentPageNumber = currentPageNumber;
				    	$scope.views.pageCondition.pageList = pageArray;
				}
			}
	};
	
	/**
	 * 조회결과
	 */
	$scope.results = {
			coupons: null
	};
	
	/**
	 * 쿠폰 현황을 페이지 번호 기준으로 조회
	 */
	$scope.pagedCoupons = function (pageNumber) {
		
		$log.debug('쿠폰 현황 조회(', pageNumber, ')');
		
		if(angular.isUndefined(pageNumber)) {
			pageNumber = 0;
		}
		
		$scope.views.pageCondition.page = pageNumber;
		
		CouponService.pagedCoupons($scope.views.pageCondition).then(function success(response) {
			$scope.results.coupons = response.data.content;
			$scope.views.pageCondition.setPageResult(response.data.totalPages, response.data.number);
		});
	};
	
	/**
	 * 사용자 이메일과 함께 쿠폰 정보를 저장(재시도 버젼)
	 */
	$scope.saveCouponWithRetry = function () {
		
		$log.debug('saveCouponWithRetry 시작 email:', email);
		
		var email = $scope.views.userEmail;
		if(angular.isUndefined(email) || email == '') {
			$window.alert('이메일 주소를 입력해야 합니다.');
			return;
		}
		
		//쿠폰 발급
		CouponService.saveCouponWithRetry(email).then(function success(response) {
			$window.alert('[', email, '] 쿠폰번호가 발급 되었습니다.');
			//처리후 재 조회
			$scope.pagedCoupons(0);
		}, function error(error) {
			$log.error('error:', error);
		});
	};
	
	/**
	 * dummy 데이터 생성
	 */
	$scope.createDummy = function () {
		
		var dummyCount = 20;
		
		for(var i = 0; i < dummyCount; i++) {
			var email = $scope.createRandomEmail();
			CouponService.saveCouponWithRetry(email);
		}
	};
	
	/**
	 * 무작위 이메일 주소 생성
	 */
	$scope.createRandomEmail = function () {
		
		var alphnumeric = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x',
			'y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'0','1','2','3','4','5','6','7','8','9'];
		
		var idSize = 10;
		var email = '';
		var suffix = '@gmail.com';
		
		for(var i = 0; i < idSize; i++) {
			var idx = Math.floor(Math.random() * alphnumeric.length) + 1;
			email += alphnumeric[idx];
		}
		
		return email + suffix;
	};
});