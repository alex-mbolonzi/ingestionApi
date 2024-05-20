import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmailTemplate } from 'app/shared/model/email-template.model';
import { EmailTemplateService } from './email-template.service';

@Component({
  selector: 'jhi-email-template-delete-dialog',
  templateUrl: './email-template-delete-dialog.component.html',
})
export class EmailTemplateDeleteDialogComponent {
  emailTemplate: IEmailTemplate;

  constructor(
    protected emailTemplateService: EmailTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.emailTemplateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'emailTemplateListModification',
        content: 'Deleted an emailTemplate',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-email-template-delete-popup',
  template: '',
})
export class EmailTemplateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emailTemplate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmailTemplateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.emailTemplate = emailTemplate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/email-template', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/email-template', { outlets: { popup: null } }]);
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
