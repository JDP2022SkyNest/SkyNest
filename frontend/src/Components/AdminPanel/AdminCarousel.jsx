import React, { useState } from "react";
import Carousel from "react-bootstrap/Carousel";
import First from "../AdminPanel/Images/First.png";
import Second from "../AdminPanel/Images/Second.png";
import Third from "../AdminPanel/Images/Third.png";
import Fourth from "../AdminPanel/Images/Fourth.png";
import Fifth from "../AdminPanel/Images/Fifth.png";
import Sixth from "../AdminPanel/Images/Sixth.png";

const AdminCarousel = () => {
   const [index, setIndex] = useState(0);

   const handleSelect = (selectedIndex) => {
      setIndex(selectedIndex);
   };

   return (
      <Carousel activeIndex={index} onSelect={handleSelect} variant="dark">
         <Carousel.Item interval={2500}>
            <img className="d-block w-100" src={First} alt="First slide" />
            <Carousel.Caption>
               <h1 className="text-dark bg-white p-1 rounded border border-dark">1) List of All users</h1>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={2500}>
            <img className="d-block w-100" src={Second} alt="Second slide" />
            <Carousel.Caption>
               <h2 className="text-dark bg-white p-2 rounded border border-dark">2) Promote user</h2>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={2500}>
            <img className="d-block w-100" src={Third} alt="Third slide" />
            <Carousel.Caption>
               <h2 className="text-dark bg-white p-1 rounded border border-dark">3) Demote or Disable</h2>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={2500}>
            <img className="d-block w-100" src={Sixth} alt="Fourth slide" />
            <Carousel.Caption>
               <h2 className="text-dark bg-white p-1 rounded border border-dark">4) Add To Company</h2>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={2500}>
            <img className="d-block w-100" src={Fifth} alt="Fourth slide" />
            <Carousel.Caption>
               <h2 className="text-dark bg-white p-1 rounded border border-dark">5) Remove from company</h2>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={2500}>
            <img className="d-block w-100" src={Fourth} alt="Fourth slide" />
            <Carousel.Caption>
               <h2 className="text-dark bg-white p-1 rounded border border-dark">6) Legend</h2>
            </Carousel.Caption>
         </Carousel.Item>
      </Carousel>
   );
};

export default AdminCarousel;
