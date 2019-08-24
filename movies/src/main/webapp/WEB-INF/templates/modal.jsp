<div class="modal fade" id="showUsers">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button data-dismiss="modal" class="close">&times;</button>
				<h4>Logged-in&nbsp;Users</h4>
			</div>
			<div class="modal-body">
				<ol>
					<li data-ng-repeat="user in loggedUsers">{{user.username}}</li>
				</ol>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>