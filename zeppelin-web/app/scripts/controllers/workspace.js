/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:UserUpdateCtrl
 * @description # UserUpdateCtrl 개인정보변경 - 사용자의 개인정보를 변경한다.
 * 
 * @author 박상민
 */
'use strict';

angular.module('zeppelinWebApp').controller('WorkspaceCtrl', function($scope, $route, $routeParams, $location, $rootScope, $http, Authentication, UtilService) {

	console.info('$routeParams.workspaceId', $routeParams.workspaceId);
	var workspaceId = $routeParams.workspaceId;
	
	$scope.notes = [];
		
	function init() {
		$scope.getNotebookList(workspaceId);
	}
	
	$scope.createNewNote = function() {    
    $rootScope.$emit('sendNewEvent', {op: 'NEW_NOTE', data:{userId: Authentication.getId(), workspaceId:workspaceId}});
  };
  
  $scope.$on('setNoteMenu', function(event, notes) {
  	//$scope.notes = notes;
  	$scope.getNotebookList(workspaceId);
  });
  
  $scope.getNotebookList = function(pWorkspaceId) {
  	UtilService.httpPost('/workspace/getNotebookList', {workspaceId: pWorkspaceId}).then(function(result) {
  		$scope.notes = result;
  	}, function(error) {
  		alert(error);
  	});
  };
  
  $scope.manage = function() {
	  $location.path('/workspaceWizard/1');
  };
  
  $scope.goNotebook = function(note) {    
  	$location.path('/notebook/' + note.noteId);
  };

  init();
});
// / @endcond