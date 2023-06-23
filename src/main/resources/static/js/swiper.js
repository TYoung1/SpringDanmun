var swiper = new Swiper(".mySwiper", {
    spaceBetween: 0,
    speed: 500,
    slidesPerGroup : 1,
    centeredSlides: false,
    loop:true,
    autoplay: {
      delay: 3500,
      disableOnInteraction: false,
    },
    slidesPerView:1,
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
  });