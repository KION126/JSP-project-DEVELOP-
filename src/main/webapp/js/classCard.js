var toggleClassCard = document.getElementById("recommendation-card");

toggleClassCard.addEventListener("mouseenter", function() {
	openSidebar();
});

toggleClassCard.addEventListener("mouseleave", function() {
	closeSidebar();
});

function openSidebar() {
	var sidebar = document.getElementById("classCard");
	sidebar.style.display = "block";
}

function closeSidebar() {
	var sidebar = document.getElementById("classCard");
	sidebar.style.display = "none";
}