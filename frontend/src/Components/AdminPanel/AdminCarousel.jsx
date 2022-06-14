import React, { useState } from "react";
import Carousel from "react-bootstrap/Carousel";
import First from "../AdminPanel/Images/First.png";
import Second from "../AdminPanel/Images/Second.png";
import Third from "../AdminPanel/Images/Third.png";

const AdminCarousel = () => {
   const [index, setIndex] = useState(0);

   const handleSelect = (selectedIndex) => {
      setIndex(selectedIndex);
   };

   return (
      <Carousel activeIndex={index} onSelect={handleSelect} variant="dark">
         <Carousel.Item interval={1600}>
            <img className="d-block w-100" src={First} alt="First slide" />
            <Carousel.Caption>
               <h3 className="text-dark bg-white p-1 rounded border border-dark">List of All users</h3>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={1600}>
            <img className="d-block w-100" src={Second} alt="Second slide" />
            <Carousel.Caption>
               <h3 className="text-dark bg-white p-2 rounded border border-dark">Select a user</h3>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item interval={1600}>
            <img className="d-block w-100" src={Third} alt="Third slide" />
            <Carousel.Caption>
               <h3 className="text-dark bg-white p-1 rounded border border-dark">Delete, Promote or Demote Them</h3>
            </Carousel.Caption>
         </Carousel.Item>
      </Carousel>
   );
};

export default AdminCarousel;
