import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IValidationNotes } from 'app/shared/model/validation-notes.model';
import { ValidationNotesService } from './validation-notes.service';

@Component({
  selector: 'jhi-validation-notes-delete-dialog',
  templateUrl: './validation-notes-delete-dialog.component.html',
})
export class ValidationNotesDeleteDialogComponent {
  validationNotes: IValidationNotes;

  constructor(
    protected validationNotesService: ValidationNotesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.validationNotesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'validationNotesListModification',
        content: 'Deleted an validationNotes',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-validation-notes-delete-popup',
  template: '',
})
export class ValidationNotesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ validationNotes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ValidationNotesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.validationNotes = validationNotes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/validation-notes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/validation-notes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
