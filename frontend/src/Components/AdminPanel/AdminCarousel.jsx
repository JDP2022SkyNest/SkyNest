import React, { useState } from "react";
import Carousel from "react-bootstrap/Carousel";
import First from "../AdminPanel/Images/First.png";
import Second from "../AdminPanel/Images/Second.png";
import Third from "../AdminPanel/Images/Third.png";

const AdminCarousel = () => {
   const [index, setIndex] = useState(0);

   const handleSelect = (selectedIndex, e) => {
      setIndex(selectedIndex);
   };

   return (
      <Carousel activeIndex={index} onSelect={handleSelect} variant="dark">
         <Carousel.Item>
            <img className="d-block w-100" src={First} alt="First slide" />
            <Carousel.Caption>
               <h3 className="text-dark">List of All users</h3>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item>
            <img className="d-block w-100" src={Second} alt="Second slide" />
            <Carousel.Caption>
               <h3 className="text-dark">Secect a user</h3>
            </Carousel.Caption>
         </Carousel.Item>
         <Carousel.Item>
            <img className="d-block w-100" src={Third} alt="Third slide" />
            <Carousel.Caption>
               <h3 className="text-dark">Delete, Promote or Demote Them</h3>
            </Carousel.Caption>
         </Carousel.Item>
      </Carousel>
   );
};

export default AdminCarousel;
