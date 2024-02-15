import React from 'react';

const CloseButton = ({onClose}) => {
    return (
        <button className="close-button" onClick={onClose}>
            X
        </button>
    );
}

export default CloseButton;