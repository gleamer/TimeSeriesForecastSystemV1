/**
 * 注册
 * signup.html
 */
var app = angular.module('signupapp', []);

app.controller("signupcontrol",function($scope,$http){
	$scope.$watch('InputPassword2',function() {$scope.check2();});
	   $scope.$watch('InputPassword1',function() {$scope.check2();});
	   $scope.check2=function(){
		   if($scope.InputPassword1!=$scope.InputPassword2){
			   $scope.pwdCheck=true;
			   
		   }else{
			   $scope.pwdCheck=false;
		   }
	   }
	
	$scope.reset = function() {
    	$scope.InputEmail ="" ;
    	$scope.InputPassword1="";
    	$scope.InputPassword2="";
    	$scope.InputUsername="";
    };
	
    $scope.register=function(){
    	//alert("submit coming ")
    	
    	
    	$http({
    		method: 'POST',
    		url: '/usersignup',
    		params:{"emailu": $scope.InputEmail,  
      		"passwordu": $scope.InputPassword2,
      		"nicknameu": $scope.InputUsername
      		}
    	
    	}).then(function successCallback(response) {
    			// 请求成功执行代码
    			
    			 console.log(response);
    			 console.log(response.data.email);
    			 if(response.data>0)
    			{
    				
    				 alert("注册成功，请登录")
    				// $location.path('/home.html');
    				 $(location).attr("href","signin.html")
    			}
    				 
    			 else{alert("注册失败,邮箱可能已注册,重新注册")} 

    			//$scope.userInputEmail=response.data.email;
    		}, function errorCallback(response) {
    			// 请求失败执行代码
    			alert("请求失败")
    			alert(response)
    	
    		}
    	
    		)

    	
    	
    	
    };
	

	
});

