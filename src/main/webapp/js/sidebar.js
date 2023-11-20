document.getElementById("toggleSidebar").addEventListener("click", function(e) {
  e.preventDefault();
  toggleSidebar();
});

function toggleSidebar() {
  var sidebar = document.getElementById("sidebar");
  if (sidebar.style.height === "0px" || sidebar.style.height === "") {
    sidebar.style.height = "300px";
    sidebar.style.width = "100%";
    sidebar.style.top = '80px';
  } else {
    sidebar.style.height = "0px";
  }
}