function deleteHomework(id) {
    fetch("/homework?homework_id=" + id, {method: "DELETE"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Delete homework error: " + response.statusText);
            }
            return response;
        }).then(_ => location.reload());
}
