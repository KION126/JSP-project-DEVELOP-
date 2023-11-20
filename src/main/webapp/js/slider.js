$(document).ready(function(){
  let loopInterval = setInterval(() => {
    $('#btn-slide-next').click();
  }, 4000);
  
  $('#carouselExampleIndicators').mouseenter(() => {
    clearInterval(loopInterval);
  });
  
  $('#carouselExampleIndicators').mouseleave(() => {
    loopInterval = setInterval(() => {
      $('#btn-slide-next').click();
    }, 4000);
  });
});
