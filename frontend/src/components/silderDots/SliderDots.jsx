import React from "react";

const SliderDots = ({slides, goToSlide, currentIndex}) => {
    return <div className="slider-dots">
        {slides.map((_, index) => <>
                        <span
                            key={index}
                            className={`dot ${index === currentIndex ? 'active' : ''}`}
                            onClick={() => goToSlide(index)}
                        />
            </>
        )}
    </div>
}

export default SliderDots
