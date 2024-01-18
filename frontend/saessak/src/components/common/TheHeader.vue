<template>
	<div id="header-layout">
		<div class="flex justify-between items-center">
			<RouterLink to="/">Logo</RouterLink>
			<div>
				<RouterLink to="/setting">설정</RouterLink>
				<RouterLink to="/chat">채팅</RouterLink>
				<RouterLink to="/alram">알람</RouterLink>
			</div>
		</div>
	</div>
</template>

<script></script>

<style scoped>
#header-layout {
	border: solid 1px black;
	height: 50px;
}
</style>
